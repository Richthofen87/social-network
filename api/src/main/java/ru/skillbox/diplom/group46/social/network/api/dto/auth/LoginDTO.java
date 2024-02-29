package ru.skillbox.diplom.group46.social.network.api.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    private String email;
    private String password;
    private String token;
}

