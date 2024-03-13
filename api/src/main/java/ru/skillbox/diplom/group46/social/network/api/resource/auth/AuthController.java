package ru.skillbox.diplom.group46.social.network.api.resource.auth;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.*;
import ru.skillbox.diplom.group46.social.network.api.dto.captcha.CaptchaDto;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
public interface AuthController {

    @PostMapping("/register")
    ResponseEntity<?> register(@RequestBody RegistrationDto registrationDto);

    @PostMapping("/refresh")
    ResponseEntity<AuthenticateResponseDto> refreshToken(@RequestBody AuthenticateResponseDto authenticateResponseDto);

    @PostMapping("/password/recovery/{recoveryTokenId}")
    ResponseEntity<String> recoverPassword(
            @PathVariable("recoveryTokenId") String recoveryTokenId,
            @RequestBody NewPasswordDto newPasswordDto
    );

    @PostMapping("/password/recovery/")
    ResponseEntity<?> sendRecoveryEmail(@RequestBody PasswordRecoveryDto passwordRecoveryDto);

    @PostMapping("/logout")
    ResponseEntity<?> logout(HttpServletResponse response) throws IOException;

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody AuthenticateDto authenticateDto);

    @PostMapping("/change-password-link")
    ResponseEntity<String> changePasswordLink(@RequestBody PasswordChangeDto passwordChangeDto);

    @PostMapping("/change-email-link")
    ResponseEntity<String> changeEmailLink(@RequestBody ChangeEmailDto changeEmailDto);

    @DeleteMapping("/admin/revokeUserTokens/{email}")
    ResponseEntity<String> revokeUserTokens(@PathVariable String email);

    @DeleteMapping("/admin/revokeAllTokens")
    ResponseEntity<String> revokeAllTokens();

    @GetMapping("/captcha")
    ResponseEntity<CaptchaDto> getCaptcha();

}
