package ru.skillbox.diplom.group46.social.network.api.dto.captcha;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CaptchaDto {
    private UUID secret;
    private String image;
}