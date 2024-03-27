package ru.skillbox.diplom.group46.social.network.impl.service.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.*;
import ru.skillbox.diplom.group46.social.network.api.exception.auth.AuthenticationError;
import ru.skillbox.diplom.group46.social.network.domain.auth.RecoveryToken;
import ru.skillbox.diplom.group46.social.network.domain.user.User;
import ru.skillbox.diplom.group46.social.network.impl.auth.security.TokenGenerator;
import ru.skillbox.diplom.group46.social.network.impl.repository.auth.RecoveryTokenRepository;
import ru.skillbox.diplom.group46.social.network.impl.repository.user.UserRepository;
import ru.skillbox.diplom.group46.social.network.impl.service.user.UserService;
import ru.skillbox.diplom.group46.social.network.impl.utils.auth.CurrentUserExtractor;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final TokenGenerator tokenGenerator;
    private final PasswordEncoder passwordEncoder;
    private final CaptchaService captchaService;
    private final UserRepository userRepository;
    private final RecoveryTokenService recoveryTokenService;
    private final EmailService emailService;
    private final RecoveryTokenRepository recoveryTokenRepository;
    private final TokenRevocationService tokenRevocationService;
    private final PasswordChangeService passwordChangeService;
    private final EmailChangeService emailChangeService;
    private final TokenRefreshService tokenRefreshService;

    public ResponseEntity<?> createNewUser(RegistrationDto registrationDto) {
        captchaService.checkCaptcha(registrationDto);
        log.debug("Creating new user: {}", registrationDto.getEmail());

        if (!registrationDto.getPassword1().equals(registrationDto.getPassword2())) {
            return ResponseEntity.badRequest().body(
                    new AuthenticationError(HttpStatus.BAD_REQUEST.value(), "Пароли не совпадают"));
        }

        User user = userService.createNewUser(registrationDto);
        return ResponseEntity.ok(new UserDTO(user.getId(), user.getFirstName(), user.getPassword(), user.getEmail()));
    }

    public ResponseEntity<AuthenticateResponseDto> refreshToken(RefreshDto refreshDto) {
        AuthenticateResponseDto responseDto = tokenRefreshService.refreshToken(refreshDto);
        if (responseDto != null) {
            return ResponseEntity.ok(responseDto);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    public ResponseEntity<String> recoverPassword(String recoveryTokenId, NewPasswordDto newPasswordDto) {
        log.debug("Recovering password with recoveryTokenId: {}", recoveryTokenId);

        RecoveryToken recoveryToken = recoveryTokenRepository.findByToken(recoveryTokenId);
        if (recoveryToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    "Неверная или просроченная ссылка для восстановления пароля");
        }

        User currentUser = recoveryToken.getUser();

        if (recoveryToken.isExpired()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    "Срок действия токена восстановления пароля истёк");
        }

        String newPassword = newPasswordDto.getPassword();
        currentUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(currentUser);

        recoveryTokenService.deleteByToken(recoveryTokenId);

        return ResponseEntity.ok(
                "Пароль успешно изменён!");
    }

    public ResponseEntity<String> sendRecoveryEmail(PasswordRecoveryDto passwordRecoveryDto) {
        log.debug("Sending recovery email for user: {}", passwordRecoveryDto.getEmail());

        String email = passwordRecoveryDto.getEmail();

        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Нет пользователя с указанной почтой");
        }

        RecoveryToken recoveryToken = recoveryTokenService.generateToken(user);
        emailService.sendRecoveryEmail(email, recoveryToken.getToken());

        return ResponseEntity.ok("Письмо на почту отправлено");
    }

    public ResponseEntity<?> logout(HttpServletResponse response) {

        String userEmail = CurrentUserExtractor.getCurrentUserFromAuthentication().getEmail();
        log.debug("Logging out user: {}", userEmail);

        try {
            tokenRevocationService.revokeUserTokensByEmail(userEmail);
        } catch (RuntimeException e) {
            log.error("Ошибка при отзыве токенов пользователя {} при выходе из системы", userEmail, e);
        }

        SecurityContextHolder.clearContext();

        Cookie cookie = new Cookie("jwt", null);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.ok("Выход из системы успешно выполнен");
    }

    public AuthenticateResponseDto createAuthToken(AuthenticateDto authenticateDto) {
        log.debug("Logging in user: {}", authenticateDto.getEmail());

        User user = userService.findByEmail(authenticateDto.getEmail());
        if (user == null || !passwordEncoder.matches(authenticateDto.getPassword(), user.getPassword())) {
            throw new AuthenticationError(HttpStatus.UNAUTHORIZED.value(), "Неправильный логин или пароль");
        }

        long accessTokenExpirationSeconds = 15 * 60; // 15 минут
        long refreshTokenExpirationSeconds = 24 * 60 * 60; // 24 часа

        AuthenticateResponseDto authenticateResponseDto = tokenGenerator.createToken(user);

        tokenRevocationService.addToken(
                authenticateResponseDto.getAccessToken(),
                user.getId().toString(),
                Instant.now().plusSeconds(accessTokenExpirationSeconds).toEpochMilli()
        );

        tokenRevocationService.addToken(
                authenticateResponseDto.getRefreshToken(),
                user.getId().toString(),
                Instant.now().plusSeconds(refreshTokenExpirationSeconds).toEpochMilli()
        );

        return authenticateResponseDto;
    }

    public ResponseEntity<String> changePassword(PasswordChangeDto passwordChangeDto) {
        log.debug("Changing password for user: ");

        return passwordChangeService.changePassword(passwordChangeDto);
    }

    public ResponseEntity<String> changeEmailLink(ChangeEmailDto changeEmailDto) {
        log.debug("Changing email for user: {}", changeEmailDto.getEmail());

        return emailChangeService.changeEmail(changeEmailDto);
    }

    public ResponseEntity<String> revokeUserTokens(String email) {
        log.debug("Revoking tokens for user: {}", email);

        boolean success = tokenRevocationService.revokeUserTokensByEmail(email);
        if (success) {
            return ResponseEntity.ok("Access и refresh токены пользователя удалены из списка активных");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при отзыве токенов пользователя");
        }
    }

    public ResponseEntity<String> revokeAllTokens() {
        log.debug("Revoking all tokens");

        boolean success = tokenRevocationService.revokeAllTokens();
        if (success) {
            return ResponseEntity.ok("Все текущие сессии закрыты");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при отзыве токенов всех пользователей");
        }
    }

}
