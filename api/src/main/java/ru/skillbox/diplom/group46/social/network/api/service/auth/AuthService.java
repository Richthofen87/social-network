package ru.skillbox.diplom.group46.social.network.api.service.auth;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.LoginDTO;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.SignupDTO;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.TokenDTO;

public interface AuthService {
    ResponseEntity<?> createNewUser(SignupDTO signupDTO);
    ResponseEntity<?> createAuthToken(LoginDTO loginDTO, HttpServletResponse response);
    TokenDTO refreshToken(TokenDTO tokenDTO);
    ResponseEntity<?> logout(HttpServletResponse response);
}
