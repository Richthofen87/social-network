package ru.skillbox.diplom.group46.social.network.impl.resource.auth;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.*;
import ru.skillbox.diplom.group46.social.network.api.dto.captcha.CaptchaDto;
import ru.skillbox.diplom.group46.social.network.api.resource.auth.AuthController;
import ru.skillbox.diplom.group46.social.network.impl.service.auth.AuthService;
import ru.skillbox.diplom.group46.social.network.impl.service.auth.CaptchaService;


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
    public ResponseEntity<?> refreshToken(@RequestBody AuthenticateResponseDto authenticateResponseDto) {
        return ResponseEntity.ok(authService.refreshToken(authenticateResponseDto));
    }

    /*@Override
    public ResponseEntity<String> recoverPassword(
            String recoveryTokenId,
            @RequestBody NewPasswordDto newPasswordDto
    ) {
        ResponseEntity<?> response = authService.recoverPassword(recoveryTokenId, newPasswordDto);
        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok("Пароль успешно изменён");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверная или просроченная ссылка");
        }
    }

    @Override
    public ResponseEntity<?> sendRecoveryEmail(PasswordRecoveryDto passwordRecoveryDto) {
        ResponseEntity<?> response = authService.sendRecoveryEmail(passwordRecoveryDto);
        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok("Письмо на почту отправлено");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Нет пользователя с указанной почтой");
        }
    }*/

    @Override
    public ResponseEntity<?> logout(HttpServletResponse response) {
        authService.logout(response);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> login(@RequestBody AuthenticateDto authenticateDto, HttpServletResponse response) {
        return ResponseEntity.ok(authService.createAuthToken(authenticateDto, response));
    }

    /*@Override
    public ResponseEntity<?> changePasswordLink(@RequestBody PasswordChangeDto passwordChangeDto) {
        ResponseEntity<?> response = authService.changePassword(passwordChangeDto);
        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok("Пароль успешно изменён");
        } else {
            return ResponseEntity.badRequest().body(response.getBody());
        }
    }

    @Override
    public ResponseEntity<?> changeEmailLink(@RequestBody ChangeEmailDto changeEmailDto) {
        ResponseEntity<?> response = authService.changeEmail(changeEmailDto);
        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok("Email успешно изменён");
        } else {
            return ResponseEntity.badRequest().body(response.getBody());
        }
    }*/

    @Override
    public ResponseEntity<CaptchaDto> getCaptcha() {
        return ResponseEntity.ok(captchaService.getCaptcha());
    }

}
