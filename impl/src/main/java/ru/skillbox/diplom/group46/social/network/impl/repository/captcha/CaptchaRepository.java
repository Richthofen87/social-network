package ru.skillbox.diplom.group46.social.network.impl.repository.captcha;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.diplom.group46.social.network.domain.captcha.CaptchaEntity;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public interface CaptchaRepository extends JpaRepository<CaptchaEntity, UUID> {

    void deleteByIssuedAtGreaterThanEqual(ZonedDateTime expiredDateTime);

    CaptchaEntity findByCode(String test);
}