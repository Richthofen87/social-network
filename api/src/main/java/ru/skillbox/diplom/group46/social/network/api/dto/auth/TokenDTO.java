package ru.skillbox.diplom.group46.social.network.api.dto.auth;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenDTO {
    private String userId;
    private String accessToken;
    private String refreshToken;

}

