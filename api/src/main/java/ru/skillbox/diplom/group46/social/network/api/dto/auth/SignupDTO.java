package ru.skillbox.diplom.group46.social.network.api.dto.auth;

import lombok.Data;

import java.util.UUID;

@Data
public class SignupDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password1;
    private String password2;
    private String captchaCode;
    private UUID captchaSecret;
}