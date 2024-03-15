package ru.skillbox.diplom.group46.social.network.impl.service.auth;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.AuthenticateResponseDto;
import ru.skillbox.diplom.group46.social.network.domain.user.User;
import ru.skillbox.diplom.group46.social.network.impl.auth.security.TokenGenerator;
import ru.skillbox.diplom.group46.social.network.impl.repository.user.UserRepository;
import ru.skillbox.diplom.group46.social.network.impl.utils.auth.CurrentUserExtractor;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenRefreshService {

    private final TokenGenerator tokenGenerator;
    private final UserRepository userRepository;

    public AuthenticateResponseDto refreshToken(String refreshToken) {
        log.debug("Method refreshToken started: {}", refreshToken);

        UUID userId = CurrentUserExtractor.getCurrentUser().getId();

        User currentUser = userRepository.findById(userId).orElse(null);
        if (currentUser == null) {
            log.error("User not found for refresh token");
            return null;
        }

        return tokenGenerator.createToken(currentUser);
    }
}
