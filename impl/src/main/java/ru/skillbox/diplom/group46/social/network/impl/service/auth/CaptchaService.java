package ru.skillbox.diplom.group46.social.network.impl.service.auth;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.GradiatedBackgroundProducer;
import cn.apiclub.captcha.noise.CurvedLineNoiseProducer;
import cn.apiclub.captcha.text.producer.FiveLetterFirstNameTextProducer;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.RegistrationDto;
import ru.skillbox.diplom.group46.social.network.api.dto.captcha.CaptchaDto;
import ru.skillbox.diplom.group46.social.network.domain.captcha.CaptchaEntity;
import ru.skillbox.diplom.group46.social.network.domain.exception.captcha.CaptchaException;
import ru.skillbox.diplom.group46.social.network.impl.repository.captcha.CaptchaRepository;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CaptchaService {

    String IMAGE_FORMAT = "jpg";
    @Value("${captcha.expiration}")
    Duration EXPIRATION;
    String PREFIX = "data:image/" + IMAGE_FORMAT + ";base64, ";
    private final CaptchaRepository repository;

    public CaptchaDto getCaptcha() {
        log.debug("Method getCaptcha() started");
        Captcha captcha = new Captcha.Builder(200, 60)
                .addBackground(new GradiatedBackgroundProducer())
                .addText(new FiveLetterFirstNameTextProducer())
                .addNoise(new CurvedLineNoiseProducer())
                .build();
        String image;
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            ImageIO.write(captcha.getImage(), IMAGE_FORMAT, os);
            image = PREFIX + Base64.getEncoder().encodeToString(os.toByteArray());
        } catch (IOException e) {
            throw new CaptchaException("Writing image failed", e);
        }
        CaptchaEntity captchaEntity = save(captcha.getAnswer());
        return new CaptchaDto(captchaEntity.getId(), image);
    }

    private CaptchaEntity save(String answer) {
        log.debug("Method save(%s) started with param: \"%s\"".formatted(String.class, answer));
        return repository.save(new CaptchaEntity(UUID.randomUUID(),
                ZonedDateTime.now(), answer));
    }

    public void checkCaptcha(RegistrationDto registrationDto) {
        log.debug("Method checkCaptcha(%s) started with param: \"%s\"".formatted(RegistrationDto.class, registrationDto));
        UUID captchaId = registrationDto.getCaptchaSecret();
        CaptchaEntity captcha = repository.findById(captchaId)
                .orElseThrow(() -> new EntityNotFoundException("Captcha with id: \"%s\" not found".formatted(captchaId)));
        if (!ZonedDateTime.now().isBefore(captcha.getIssuedAt().plus(EXPIRATION)))
            throw new CaptchaException("Captcha with id: \"%s\" has expired".formatted(captchaId));
        if (!registrationDto.getCaptchaCode().equals(captcha.getCode()))
            throw new CaptchaException("Provided code \"%s\" is not valid for captcha with id: \"%s\""
                    .formatted(captcha.getCode(), captchaId));
    }

    @Transactional
    @Scheduled(initialDelayString = "${captcha.checkDelay}", fixedDelayString = "${captcha.checkDelay}")
    protected void clearCaptcha() {
        repository.deleteByIssuedAtGreaterThanEqual(ZonedDateTime.now().plus(EXPIRATION));
    }
}