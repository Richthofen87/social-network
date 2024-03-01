package ru.skillbox.diplom.group46.social.network.api.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticateDto {
    private String email;
    private String password;
}

