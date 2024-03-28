package ru.skillbox.diplom.group46.social.network.impl.service.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group46.social.network.domain.user.User;
import ru.skillbox.diplom.group46.social.network.impl.repository.user.UserRepository;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenRevocationService {

    private final UserRepository userRepository;

    private final Map<String, List<String>> activeTokens = new ConcurrentHashMap<>(); // Для активных токенов
    private final Map<String, Long> tokenExpirations = new ConcurrentHashMap<>(); // Для времени истечения токенов
    private final Set<String> usedRefreshTokens = new HashSet<>(); // Список использованных токенов

    public synchronized boolean revokeUserTokensByEmail(String email) {
        log.debug("Revoking tokens for user with email: {}", email);

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String userId = user.getId().toString();

        List<String> tokens = activeTokens.get(userId);
        if (tokens != null && !tokens.isEmpty()) {
            for (String token : new ArrayList<>(tokens)) {
                revokeToken(token);
            }
            activeTokens.remove(userId);
            return true;
        } else {
            return false; // нет активных токенов для отзыва
        }
    } else {
        // пользователь не найден
        return false;
    }
}

    public boolean revokeAllTokens() { // ТОЛЬКО ДЛЯ АДМИНА
        log.debug("Revoking all tokens");

        activeTokens.clear();
        tokenExpirations.clear();
        log.debug("All current sessions have been closed");

        return true;
    }


    private void revokeToken(String token) {
        for (Map.Entry<String, List<String>> entry : activeTokens.entrySet()) {
            if (entry.getValue().contains(token)) {
                entry.getValue().remove(token);
                tokenExpirations.remove(token);
                log.debug("Токен {} был успешно отозван.", token);
                return;
            }
        }
        throw new IllegalArgumentException("Token not found in the list of active tokens");
    } // Возможно просто вывод сообщения об ошибке

    @Scheduled(fixedDelay = 24 * 60 * 60 * 1000) // каждые 24 часа
    public void cleanInactiveTokens() {
        log.debug("Cleaning up inactive tokens");

        usedRefreshTokens.clear(); // Очистка списка использованных токенов

        Map<String, List<String>> copyActiveTokens = new ConcurrentHashMap<>(activeTokens);

        copyActiveTokens.forEach((email, tokens) -> {
            tokens.removeIf(this::isTokenExpired);
            if (tokens.isEmpty()) {
                activeTokens.remove(email);
            }
        });
    } //  Вместо этого можно использовать итератор для безопасного удаления токенов.

    private boolean isTokenExpired(String token) {
        Long expirationTime = tokenExpirations.get(token);
        if (expirationTime == null) {
            return false;
        }
        return Instant.ofEpochMilli(expirationTime).isBefore(Instant.now());
    }

    public void addToken(String token, String userId, long expirationTime) {
        activeTokens.computeIfAbsent(userId, k -> new ArrayList<>()).add(token);
        tokenExpirations.put(token, expirationTime);
        log.debug("Token {} has been successfully added for user {}.", token, userId);
    }

    public boolean isActive(String token, String userId) {
        List<String> userTokens = activeTokens.getOrDefault(userId, new ArrayList<>());
        if (!userTokens.contains(token)) {
            return false; // Токен не найден
        }

        Long expirationTime = tokenExpirations.get(token);
        if (expirationTime == null) {
            return false; // Время истечения токена неизвестно
        }
        return Instant.ofEpochMilli(expirationTime).isAfter(Instant.now());
    }

    public synchronized boolean isRefreshTokenUsed(String refreshToken) {
        return usedRefreshTokens.contains(refreshToken);
    }

    public synchronized void markRefreshTokenAsUsed(String refreshToken) {
        usedRefreshTokens.add(refreshToken);
    }

    public String getActiveUsersAsString() {

        StringBuilder activeUsers = new StringBuilder();

        activeTokens.forEach((userId, tokens) -> {
            activeUsers.append(userId).append("\n");
        });

        return activeUsers.toString();
    }

}
