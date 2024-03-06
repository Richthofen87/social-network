/*
package ru.skillbox.diplom.group46.social.network.impl.service.auth;

import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.RecoveryToken;
import ru.skillbox.diplom.group46.social.network.domain.user.User;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
@Service
public class RecoveryTokenService {
    private Map<String, RecoveryToken> recoveryTokens = new HashMap<>();

    public RecoveryToken generateToken(User user) {
        String token = generateRandomToken();
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(24);
        RecoveryToken recoveryToken = new RecoveryToken();
        recoveryToken.setToken(token);
        recoveryToken.setExpiryDate(expiryDate);
        // recoveryToken.setUser(user);
        recoveryTokens.put(token, recoveryToken);
        return recoveryToken;
    }

    public void delete(RecoveryToken recoveryToken) {
        recoveryTokens.remove(recoveryToken.getToken());
    }

    private String generateRandomToken() {
        return "random_token";
    }
}*/

