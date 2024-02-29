package ru.skillbox.diplom.group46.social.network.api.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.skillbox.diplom.group46.social.network.domain.user.User;

import java.util.UUID;


@Data
@AllArgsConstructor
public class UserDTO {

    private String id;

    private String username;

    private String email;

}
