package ru.skillbox.diplom.group46.social.network.api.resource.auth;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.LoginDTO;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.SignupDTO;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.TokenDTO;
import ru.skillbox.diplom.group46.social.network.api.dto.captcha.CaptchaDto;

@RestController
@RequestMapping("/api/v1/auth")
public interface AuthController {

    @PostMapping("/register")
    ResponseEntity<?> register(@RequestBody SignupDTO signupDTO);

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response);

    @PostMapping("/token")
    ResponseEntity<?> token(@RequestBody TokenDTO tokenDTO);

    @PostMapping("/logout")
    ResponseEntity<?> logout(HttpServletResponse response);

    @GetMapping("/captcha")
    ResponseEntity<CaptchaDto> getCaptcha();

}
