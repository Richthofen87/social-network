package ru.skillbox.diplom.group46.social.network.impl.service.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
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
import ru.skillbox.diplom.group46.social.network.api.exception.auth.AppError;
import ru.skillbox.diplom.group46.social.network.domain.user.User;
import ru.skillbox.diplom.group46.social.network.domain.user.role.Role;
import ru.skillbox.diplom.group46.social.network.impl.auth.configs.JwtAuthenticationToken;
import ru.skillbox.diplom.group46.social.network.impl.auth.security.TokenGenerator;
import ru.skillbox.diplom.group46.social.network.impl.repository.user.UserRepository;
import ru.skillbox.diplom.group46.social.network.impl.service.role.RoleService;
import ru.skillbox.diplom.group46.social.network.impl.service.user.UserService;
import ru.skillbox.diplom.group46.social.network.impl.utils.auth.CurrentUserExtractor;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private TokenGenerator tokenGenerator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    CaptchaService captchaService;

    @Autowired
    @Qualifier("jwtRefreshTokenAuthProvider")
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> createNewUser(RegistrationDto registrationDto) {
        captchaService.checkCaptcha(registrationDto);
        if (!registrationDto.getPassword1().equals(registrationDto.getPassword2())) {
            return ResponseEntity.badRequest().body(new AppError(HttpStatus.BAD_REQUEST.value(), "Пароли не совпадают"));
        }

        User user = userService.createNewUser(registrationDto);
        return ResponseEntity.ok(new UserDTO(user.getId().toString(), user.getFirstName(), user.getEmail()));
    }

    public ResponseEntity<?> createAuthToken(AuthenticateDto authenticateDto, HttpServletResponse response) {
        try {
            User user = userService.findByEmail(authenticateDto.getEmail());
            if (user == null || !passwordEncoder.matches(authenticateDto.getPassword(), user.getPassword())) {
                throw new BadCredentialsException("Неправильный логин или пароль");
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
        } catch (org.springframework.security.core.AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AppError(HttpStatus.UNAUTHORIZED.value(), "Неправильный логин или пароль"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AppError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Внутренняя ошибка сервера"));
        }
    }

    public AuthenticateResponseDto refreshToken(AuthenticateResponseDto authenticateResponseDto) {
        Authentication authentication = jwtAuthenticationProvider.authenticate(
                new BearerTokenAuthenticationToken(authenticateResponseDto.getRefreshToken()));
        Jwt jwt = (Jwt) authentication.getCredentials();

        return tokenGenerator.createToken(authentication);
    }

    public ResponseEntity<?> logout(HttpServletResponse response) {
        try {
            SecurityContextHolder.clearContext();

            Cookie cookie = new Cookie("access_token", null);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(0);
            response.addCookie(cookie);

            return ResponseEntity.ok("Выход из системы успешно выполнен");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AppError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ошибка выхода из системы"));
        }
    }

    public ResponseEntity<?> changePassword(PasswordChangeDto passwordChangeDto) {
        User currentUser = CurrentUserExtractor.getCurrentUser();
        if (currentUser == null) {
            return ResponseEntity.badRequest().body(new AppError(
                    400, "Пользователь не найден"));
        }

        String newPassword1 = passwordChangeDto.getNewPassword1();
        String newPassword2 = passwordChangeDto.getNewPassword2();
        if (!newPassword1.equals(newPassword2)) {
            return ResponseEntity.badRequest().body(new AppError(
                    400, "Введённые пароли не совпадают"));
        }

        currentUser.setPassword(passwordEncoder.encode(newPassword1));
        userRepository.save(currentUser);

        return ResponseEntity.ok("Пароль успешно изменён");

    }

    public ResponseEntity<?> changeEmail(ChangeEmailDto changeEmailDto) {
        User currentUser = CurrentUserExtractor.getCurrentUser();
        if (currentUser == null) {
            return ResponseEntity.badRequest().body(new AppError(
                    400, "Пользователь не найден"));
        }

        String newEmail = changeEmailDto.getEmail().getEmail();
        if (userRepository.existsByEmail(newEmail)) {
            return ResponseEntity.badRequest().body(new AppError(
                    400, "Этот адрес электронной почты уже используется"));
        }

        currentUser.setEmail(newEmail);
        userRepository.save(currentUser);

        return ResponseEntity.ok("Email успешно изменён");
    }
}
