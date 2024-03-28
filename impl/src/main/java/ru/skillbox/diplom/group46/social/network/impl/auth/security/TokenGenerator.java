package ru.skillbox.diplom.group46.social.network.impl.auth.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import ru.skillbox.diplom.group46.social.network.api.dto.auth.AuthenticateResponseDto;

import ru.skillbox.diplom.group46.social.network.domain.user.User;


import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenGenerator {

    private final JwtEncoder accessTokenEncoder;
    private final JwtEncoder refreshTokenEncoder;

    private String createAccessToken(User user) {
        log.debug("Creating access token");
        Instant now = Instant.now();
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("myApp")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.DAYS))
                .subject(user.getId().toString())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .claim("email", user.getEmail())
                .build();
        return accessTokenEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

    public String createRefreshToken(User user) {
        log.debug("Creating refresh token");
        Instant now = Instant.now();
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("myApp")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.DAYS))
                .subject(user.getId().toString())
                .build();
        return refreshTokenEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

    public AuthenticateResponseDto createToken(User authentication) {
        log.debug("Creating tokens");
        AuthenticateResponseDto authenticateResponseDto = new AuthenticateResponseDto();
        authenticateResponseDto.setAccessToken(createAccessToken(authentication));
        authenticateResponseDto.setRefreshToken(createRefreshToken(authentication));
        return authenticateResponseDto;
    }
}
