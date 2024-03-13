package ru.skillbox.diplom.group46.social.network.impl.service.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class TokenRevocationService {

    private final Map<String, List<String>> activeTokens = new ConcurrentHashMap<>();
    private final Map<String, Long> tokenExpirations = new ConcurrentHashMap<>();

    public boolean revokeUserTokensByEmail(String email) {
        log.info("Revoking tokens for user with email: {}", email);

        List<String> tokens = activeTokens.get(email);
        if (tokens != null && !tokens.isEmpty()) {
            for (String token : tokens) {
                revokeToken(token);
            }
            activeTokens.remove(email);
            return true;
        } else {
            return false;
        }
    }

    public boolean revokeAllTokens() {
        log.info("Revoking all tokens");

        activeTokens.clear();
        tokenExpirations.clear();
        log.info("All current sessions have been closed");

        return true;
    }


    private void revokeToken(String token) {
        for (Map.Entry<String, List<String>> entry : activeTokens.entrySet()) {
            if (entry.getValue().contains(token)) {
                entry.getValue().remove(token);
                tokenExpirations.remove(token);
                log.info("Токен {} был успешно отозван.", token);
                return;
            }
        }
        throw new IllegalArgumentException("Token not found in the list of active tokens");
    }

    @Scheduled(fixedDelay = 24 * 60 * 60 * 1000) // каждые 24 часа
    public void cleanInactiveTokens() {
        log.info("Cleaning up inactive tokens");
        activeTokens.forEach((email, tokens) -> {
            tokens.removeIf(this::isTokenExpired);
            if (tokens.isEmpty()) {
                activeTokens.remove(email);
            }
        });
    }

    private boolean isTokenExpired(String token) {
        Long expirationTime = tokenExpirations.get(token);
        if (expirationTime == null) {
            return false;
        }
        return Instant.ofEpochMilli(expirationTime).isBefore(Instant.now());
    }

    public void addToken(String token, String email, long expirationTime) {
        activeTokens.computeIfAbsent(email, k -> new ArrayList<>()).add(token);
        tokenExpirations.put(token, expirationTime);
        log.info("Token {} has been successfully added for user {}.", token, email);
    }

    public boolean isActive(String token) {
        List<String> tokens = activeTokens.values().stream()
                .flatMap(List::stream)
                .toList();
        if (!tokens.contains(token)) {
            return false;
        }

        Long expirationTime = tokenExpirations.get(token);
        if (expirationTime == null) {
            return false;
        }
        return Instant.ofEpochMilli(expirationTime).isAfter(Instant.now());
    }

}
