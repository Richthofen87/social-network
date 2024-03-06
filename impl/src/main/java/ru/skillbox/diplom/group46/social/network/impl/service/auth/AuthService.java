package ru.skillbox.diplom.group46.social.network.impl.service.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.*;
import ru.skillbox.diplom.group46.social.network.api.exception.auth.AuthenticationError;
import ru.skillbox.diplom.group46.social.network.domain.auth.RecoveryToken;
import ru.skillbox.diplom.group46.social.network.domain.user.User;
import ru.skillbox.diplom.group46.social.network.domain.user.role.Role;
import ru.skillbox.diplom.group46.social.network.impl.auth.configs.JwtAuthenticationToken;
import ru.skillbox.diplom.group46.social.network.impl.auth.security.TokenGenerator;
import ru.skillbox.diplom.group46.social.network.impl.repository.auth.RecoveryTokenRepository;
import ru.skillbox.diplom.group46.social.network.impl.repository.user.UserRepository;
import ru.skillbox.diplom.group46.social.network.impl.service.role.RoleService;
import ru.skillbox.diplom.group46.social.network.impl.service.user.UserService;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final RoleService roleService;
    private final TokenGenerator tokenGenerator;
    private final PasswordEncoder passwordEncoder;
    private final CaptchaService captchaService;

    @Qualifier("jwtRefreshTokenAuthProvider")
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final UserRepository userRepository;
    private final RecoveryTokenService recoveryTokenService;
    private final EmailService emailService;
    private final RecoveryTokenRepository recoveryTokenRepository;
    private final TokenRevocationService tokenRevocationService;

    public ResponseEntity<?> createNewUser(RegistrationDto registrationDto) {
        captchaService.checkCaptcha(registrationDto);
        if (!registrationDto.getPassword1().equals(registrationDto.getPassword2())) {
            return ResponseEntity.badRequest().body(
                    new AuthenticationError(HttpStatus.BAD_REQUEST.value(), "Пароли не совпадают"));
        }

        User user = userService.createNewUser(registrationDto);
        return ResponseEntity.ok(new UserDTO(user.getId(), user.getFirstName(), user.getPassword(), user.getEmail()));
    }

    public ResponseEntity<AuthenticateResponseDto> refreshToken(AuthenticateResponseDto authenticateResponseDto) {
        Authentication authentication = jwtAuthenticationProvider.authenticate(
                new BearerTokenAuthenticationToken(authenticateResponseDto.getRefreshToken()));

        return ResponseEntity.ok(tokenGenerator.createToken(authentication));
    }

    public ResponseEntity<String> recoverPassword(String recoveryTokenId, NewPasswordDto newPasswordDto) {

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
        SecurityContextHolder.getContext().setAuthentication(null);
        SecurityContextHolder.clearContext();

        Cookie cookie = new Cookie("jwt", null);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.ok("Выход из системы успешно выполнен");
    }

    public ResponseEntity<AuthenticateResponseDto> createAuthToken(AuthenticateDto authenticateDto, HttpServletResponse response) {

        User user = userService.findByEmail(authenticateDto.getEmail());
        if (user == null || !passwordEncoder.matches(authenticateDto.getPassword(), user.getPassword())) {
            throw new AuthenticationError(HttpStatus.UNAUTHORIZED.value(), "Неправильный логин или пароль");
        }
        Role userRole = roleService.getUserRole();
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userRole.getValue()));

        JwtAuthenticationToken authenticationToken = JwtAuthenticationToken.authenticated(user, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        AuthenticateResponseDto authenticateResponseDto = tokenGenerator.createToken(authenticationToken);

        String cookieValue = String.format("jwt=%s; HttpOnly; Secure; Path=/; SameSite=None", authenticateResponseDto.getAccessToken());
        response.setHeader("Set-Cookie", cookieValue);

        return ResponseEntity.ok(authenticateResponseDto);
    }


    public ResponseEntity<String> revokeUserTokens(String email) {
        boolean success = tokenRevocationService.revokeUserTokensByEmail(email);
        if (success) {
            return ResponseEntity.ok("Access и refresh токены пользователя удалены из списка активных");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при отзыве токенов пользователя");
        }
    }

    public ResponseEntity<String> revokeAllTokens() {
        boolean success = tokenRevocationService.revokeAllTokens();
        if (success) {
            return ResponseEntity.ok("Все текущие сессии закрыты");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при отзыве токенов всех пользователей");
        }
    }

}
