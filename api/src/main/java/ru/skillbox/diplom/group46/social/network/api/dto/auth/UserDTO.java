package ru.skillbox.diplom.group46.social.network.api.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;


@Data
@AllArgsConstructor
public class UserDTO {

    private UUID id;

    private String username;

    private String email;

}
