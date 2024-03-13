package ru.skillbox.diplom.group46.social.network.api.dto.auth;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PasswordChangeDto {
    private String newPassword1;
    private String newPassword2;
}
