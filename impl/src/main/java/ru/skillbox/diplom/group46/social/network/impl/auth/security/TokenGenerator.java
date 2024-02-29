package ru.skillbox.diplom.group46.social.network.impl.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.CustomUserDetails;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.AuthenticateResponseDto;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.UserDTO;
import ru.skillbox.diplom.group46.social.network.domain.user.User;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class TokenGenerator {
    @Autowired
    JwtEncoder accessTokenEncoder;

    @Autowired
    @Qualifier("jwtRefreshTokenEncoder")
    JwtEncoder refreshTokenEncoder;

    private String createAccessToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Instant now = Instant.now();
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("myApp")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.MINUTES))
                .subject(user.getId().toString())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .claim("email", user.getEmail())
                .build();
        return accessTokenEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

    private String createRefreshToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Instant now = Instant.now();

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("myApp")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.DAYS))
                .subject(user.getId().toString())
                .build();

        return refreshTokenEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

    public AuthenticateResponseDto createToken(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        CustomUserDetails user;
        if (principal instanceof UserDTO) {
            UserDTO userDTO = (UserDTO) principal;
            user = new CustomUserDetails();
            user.setId(userDTO.getId());
            user.setUsername(userDTO.getUsername());
        } else if (principal instanceof CustomUserDetails) {
            user = (CustomUserDetails) principal;
        } else if (principal instanceof User) {
            User userObject = (User) principal;
            user = new CustomUserDetails();
            user.setId(userObject.getId().toString());
            user.setUsername(userObject.getFirstName());
        } else {
            throw new BadCredentialsException("Principal is not of expected type");
        }

        AuthenticateResponseDto authenticateResponseDto = new AuthenticateResponseDto();
        authenticateResponseDto.setUserId(user.getId());
        authenticateResponseDto.setAccessToken(createAccessToken(authentication));

        String refreshToken;
        if (authentication.getCredentials() instanceof Jwt jwt) {
            Instant now = Instant.now();
            Instant expiresAt = jwt.getExpiresAt();
            Duration duration = Duration.between(now, expiresAt);
            long daysUntilExpired = duration.toDays();
            if (daysUntilExpired < 7) {
                refreshToken = createRefreshToken(authentication);
            } else {
                refreshToken = jwt.getTokenValue();
            }
        } else {
            refreshToken = createRefreshToken(authentication);
        }
        authenticateResponseDto.setRefreshToken(refreshToken);

        return authenticateResponseDto;
    }
}
