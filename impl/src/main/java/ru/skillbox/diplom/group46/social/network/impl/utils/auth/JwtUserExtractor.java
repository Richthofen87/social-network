package ru.skillbox.diplom.group46.social.network.impl.utils.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.UserDTO;
import ru.skillbox.diplom.group46.social.network.impl.auth.configs.SecurityConfig;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUserExtractor {

    private final SecurityConfig securityConfig;

    public UserDTO getUserFromToken(String token) {
        JwtDecoder jwtDecoder = securityConfig.jwtAccessTokenDecoder();
        Jwt jwt = jwtDecoder.decode(token);

        UUID id = UUID.fromString(jwt.getClaim("sub"));
        String username = jwt.getClaim("firstName");
        String email = jwt.getClaim("email");

        return new UserDTO(id, username, null, email);
    }
}