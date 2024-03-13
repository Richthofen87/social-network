package ru.skillbox.diplom.group46.social.network.impl.resource.auth;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.*;
import ru.skillbox.diplom.group46.social.network.api.dto.captcha.CaptchaDto;
import ru.skillbox.diplom.group46.social.network.api.resource.auth.AuthController;
import ru.skillbox.diplom.group46.social.network.impl.service.auth.AuthService;
import ru.skillbox.diplom.group46.social.network.impl.service.auth.CaptchaService;
import ru.skillbox.diplom.group46.social.network.impl.service.auth.TokenRevocationService;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;
    private final CaptchaService captchaService;
    private final TokenRevocationService tokenRevocationService;

    @Override
    public ResponseEntity<?> register(@RequestBody RegistrationDto registrationDto) {
        return ResponseEntity.ok(authService.createNewUser(registrationDto));
    }

    @Override
    public ResponseEntity<AuthenticateResponseDto> refreshToken(@RequestBody AuthenticateResponseDto authenticateResponseDto) {
        return authService.refreshToken(authenticateResponseDto);
    }

    @Override
    public ResponseEntity<String> recoverPassword(
            String recoveryTokenId,
            @RequestBody NewPasswordDto newPasswordDto
    ) {
        return authService.recoverPassword(recoveryTokenId, newPasswordDto);
    }

    @Override
    public ResponseEntity<?> sendRecoveryEmail(PasswordRecoveryDto passwordRecoveryDto) {
        return authService.sendRecoveryEmail(passwordRecoveryDto);
    }

    @Override
    public ResponseEntity<?> logout(HttpServletResponse response) {
        authService.logout(response);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<AuthenticateResponseDto> login(@RequestBody AuthenticateDto authenticateDto) {

        return ResponseEntity.ok(authService.createAuthToken(authenticateDto));

    }

    @Override
    public ResponseEntity<String> changePasswordLink(@RequestBody PasswordChangeDto passwordChangeDto) {
        return authService.changePassword(passwordChangeDto);
    }

    @Override
    public ResponseEntity<String> changeEmailLink(@RequestBody ChangeEmailDto changeEmailDto) {
        ResponseEntity<String> response = authService.changeEmailLink(changeEmailDto);
        if (response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, "/login").body("");
        } else {
            return response;
        }
    }

    @Override
    public ResponseEntity<String> revokeUserTokens(String email) {
        return authService.revokeUserTokens(email);
    }

    @Override
    public ResponseEntity<String> revokeAllTokens() {
        return authService.revokeAllTokens();
    }

    @Override
    public ResponseEntity<CaptchaDto> getCaptcha() {
        return ResponseEntity.ok(captchaService.getCaptcha());
    }

}
