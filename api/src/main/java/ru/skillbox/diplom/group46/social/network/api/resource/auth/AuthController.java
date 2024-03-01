package ru.skillbox.diplom.group46.social.network.api.resource.auth;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.*;
import ru.skillbox.diplom.group46.social.network.api.dto.captcha.CaptchaDto;

@RestController
@RequestMapping("/api/v1/auth")
public interface AuthController {

    @PostMapping("/register")
    ResponseEntity<?> register(@RequestBody RegistrationDto registrationDto);

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody AuthenticateDto authenticateDto, HttpServletResponse response);

    @PostMapping("/refresh")
    ResponseEntity<?> refreshToken(@RequestBody AuthenticateResponseDto authenticateResponseDto);

    @PostMapping("/logout")
    ResponseEntity<?> logout(HttpServletResponse response);

    @GetMapping("/captcha")
    ResponseEntity<CaptchaDto> getCaptcha();

    @PostMapping("/change-password-link")
    ResponseEntity<?> changePasswordLink(@RequestBody PasswordChangeDto passwordChangeDto);

    @PostMapping("/change-email-link")
    ResponseEntity<?> changeEmailLink(@RequestBody ChangeEmailDto changeEmailDto);

}
