package ru.skillbox.diplom.group46.social.network.impl.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group46.social.network.domain.auth.RecoveryToken;

@Repository
public interface RecoveryTokenRepository extends JpaRepository<RecoveryToken, Long> {
    RecoveryToken findByToken(String token);
    void deleteByToken(String token);
}

