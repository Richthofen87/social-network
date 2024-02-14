package ru.skillbox.diplom.group46.social.network.api.dto.account;

import lombok.*;
import ru.skillbox.diplom.group46.social.network.api.dto.base.BaseDto;

/**
 * AccountDto
 *
 * @author vladimir.sazonov
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto extends BaseDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String photo;
    private String profileCover;
    private String about;
    private String city;
    private String country;
    private String regDate;
    private String statusCode;
    private String birthDate;
    private String messagePermission;
    private String lastOnlineTime;
    private String isOnline;
    private String isBlocked;
    private String emojiStatus;
    private String deletionTimestamp;
    private String createdDate;
    private String lastModifiedDate;
}