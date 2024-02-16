package ru.skillbox.diplom.group46.social.network.impl.resource.auth;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.LoginDTO;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.SignupDTO;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.TokenDTO;
import ru.skillbox.diplom.group46.social.network.api.dto.captcha.CaptchaDto;
import ru.skillbox.diplom.group46.social.network.api.resource.auth.AuthController;
import ru.skillbox.diplom.group46.social.network.api.service.auth.AuthService;
import ru.skillbox.diplom.group46.social.network.impl.service.auth.CaptchaService;


@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;
    private final CaptchaService captchaService;

    @Override
    public ResponseEntity<?> register(@RequestBody SignupDTO signupDTO) {
        return ResponseEntity.ok(authService.createNewUser(signupDTO));
    }

    @Override
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        return ResponseEntity.ok(authService.createAuthToken(loginDTO, response));
    }

    @Override
    public ResponseEntity<?> token(@RequestBody TokenDTO tokenDTO) {
        return ResponseEntity.ok(authService.refreshToken(tokenDTO));
    }

    @Override
    public ResponseEntity<?> logout(HttpServletResponse response) {
        return ResponseEntity.ok("Выход из системы успешно выполнен");
    }

    @Override
    public ResponseEntity<CaptchaDto> getCaptcha() {
        return ResponseEntity.ok(captchaService.getCaptcha());
    }
}