package ru.skillbox.diplom.group46.social.network.impl.resource.auth;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.AuthenticateDto;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.PasswordChangeDto;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.RegistrationDto;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.AuthenticateResponseDto;
import ru.skillbox.diplom.group46.social.network.api.dto.captcha.CaptchaDto;
import ru.skillbox.diplom.group46.social.network.api.resource.auth.AuthController;
import ru.skillbox.diplom.group46.social.network.impl.service.auth.AuthService;
import ru.skillbox.diplom.group46.social.network.impl.service.auth.CaptchaService;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;
    private final CaptchaService captchaService;

    @Override
    public ResponseEntity<?> register(@RequestBody RegistrationDto registrationDto) {
        return ResponseEntity.ok(authService.createNewUser(registrationDto));
    }

    @Override
    public ResponseEntity<?> login(@RequestBody AuthenticateDto authenticateDto, HttpServletResponse response) {
        return ResponseEntity.ok(authService.createAuthToken(authenticateDto, response));
    }

    @Override
    public ResponseEntity<?> refreshToken(@RequestBody AuthenticateResponseDto authenticateResponseDto) {
        return ResponseEntity.ok(authService.refreshToken(authenticateResponseDto));
    }

    @Override
    public ResponseEntity<?> logout(HttpServletResponse response) {
        authService.logout(response);
        return ResponseEntity.ok("Выход из системы успешно выполнен");
    }

    @Override
    public ResponseEntity<CaptchaDto> getCaptcha() {
        return ResponseEntity.ok(captchaService.getCaptcha());
    }

    @Override
    public ResponseEntity<?> changePasswordLink(UUID userId, @RequestBody PasswordChangeDto passwordChangeDto) {
        ResponseEntity<?> response = authService.changePassword(userId, passwordChangeDto);
        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok("Пароль успешно изменён");
        } else {
            return ResponseEntity.badRequest().body(response.getBody());
        }
    }
}