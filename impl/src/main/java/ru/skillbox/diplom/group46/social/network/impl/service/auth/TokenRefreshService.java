package ru.skillbox.diplom.group46.social.network.impl.service.auth;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.AuthenticateResponseDto;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.RefreshDto;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.UserDTO;
import ru.skillbox.diplom.group46.social.network.domain.user.User;
import ru.skillbox.diplom.group46.social.network.impl.auth.security.TokenGenerator;
import ru.skillbox.diplom.group46.social.network.impl.repository.user.UserRepository;
import ru.skillbox.diplom.group46.social.network.impl.utils.auth.CurrentUserExtractor;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenRefreshService {

    private final JwtDecoder jwtDecoder;
    private final UserRepository userRepository;
    private final TokenGenerator tokenGenerator;
    private final TokenRevocationService tokenRevocationService;


    public AuthenticateResponseDto refreshToken(RefreshDto refreshTokenDto) {
        log.debug("Method refreshToken started: {}", refreshTokenDto);

        // Проверяем, использовался ли уже этот refresh токен
        if (tokenRevocationService.isRefreshTokenUsed(refreshTokenDto.getRefreshToken())) {
            log.error("Refresh token has already been used");
            throw new RuntimeException("Refresh token has already been used");
        }

        Jwt jwt = jwtDecoder.decode(refreshTokenDto.getRefreshToken());

        if (jwt == null) {
            log.error("Token decoding failed");
            throw new RuntimeException("Token decoding failed");
        }

        UserDTO currentUserDTO = CurrentUserExtractor.getUserFromJwt(jwt);
        UUID userId = currentUserDTO.getId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found for id: " + userId));

        if (tokenRevocationService.isActive(refreshTokenDto.getRefreshToken(), userId.toString())) {

            tokenRevocationService.revokeUserTokensByEmail(user.getEmail());

            AuthenticateResponseDto newToken = tokenGenerator.createToken(user);

            // Помечаем refresh токен как использованный
            tokenRevocationService.markRefreshTokenAsUsed(refreshTokenDto.getRefreshToken());
            return newToken;
        } else {
            log.error("Refresh token has been revoked");
            throw new RuntimeException("Refresh token has been revoked");
        }
    }
}
