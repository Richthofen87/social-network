package ru.skillbox.diplom.group46.social.network.api.dto.auth;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticateResponseDto {
    private String accessToken;
    private String refreshToken;

}

