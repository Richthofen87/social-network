package ru.skillbox.diplom.group46.social.network.impl.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.diplom.group46.social.network.domain.auth.RecoveryToken;
import ru.skillbox.diplom.group46.social.network.domain.user.User;
import ru.skillbox.diplom.group46.social.network.impl.repository.auth.RecoveryTokenRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class RecoveryTokenService {

    private final RecoveryTokenRepository recoveryTokenRepository;

    public RecoveryToken generateToken(User user) {
        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(24);
        RecoveryToken recoveryToken = new RecoveryToken();
        recoveryToken.setToken(token);
        recoveryToken.setExpiryDate(expiryDate);
        recoveryToken.setUser(user);
        return recoveryTokenRepository.save(recoveryToken);
    }

    public void deleteByToken(String token) {
        recoveryTokenRepository.deleteByToken(token);
    }

}

