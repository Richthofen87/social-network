package ru.skillbox.diplom.group46.social.network.impl.utils.auth;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import ru.skillbox.diplom.group46.social.network.domain.user.User;
import ru.skillbox.diplom.group46.social.network.impl.auth.configs.SecurityConfig;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUserExtractor {

    private final SecurityConfig securityConfig;

    public User getUserFromToken(String token) {
        JwtDecoder jwtDecoder = securityConfig.jwtAccessTokenDecoder();
        Jwt jwt = jwtDecoder.decode(token);
        User user = new User();
        user.setId(UUID.fromString(jwt.getClaim("sub")));
        user.setFirstName(jwt.getClaim("firstName"));
        user.setLastName(jwt.getClaim("lastName"));
        user.setEmail(jwt.getClaim("email"));
        return user;
    }

    public String getUsernameFromToken(String token) {
        JwtDecoder jwtDecoder = securityConfig.jwtAccessTokenDecoder();
        Jwt jwt = jwtDecoder.decode(token);
        Claims claims = (Claims) jwt.getClaims();
        return claims.getSubject();
    }
}