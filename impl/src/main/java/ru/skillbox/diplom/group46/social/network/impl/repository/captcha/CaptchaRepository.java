package ru.skillbox.diplom.group46.social.network.impl.repository.captcha;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.diplom.group46.social.network.domain.captcha.CaptchaEntity;

import java.util.UUID;

public interface CaptchaRepository extends JpaRepository<CaptchaEntity, UUID> {
}
