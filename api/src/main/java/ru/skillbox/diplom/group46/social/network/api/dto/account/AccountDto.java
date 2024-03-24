package ru.skillbox.diplom.group46.social.network.api.dto.account;

import lombok.*;
import ru.skillbox.diplom.group46.social.network.api.dto.base.BaseDto;

import java.time.ZonedDateTime;

/**
 * AccountDto
 *
 * @author vladimir.sazonov
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
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
    private ZonedDateTime regDate;
    private String statusCode;
    private ZonedDateTime birthDate;
    private String messagePermission;
    private Long lastOnlineTime;
    private Boolean isOnline;
    private Boolean isBlocked;
    private String emojiStatus;
    private ZonedDateTime deletionTimestamp;
    private ZonedDateTime createdDate;
    private ZonedDateTime lastModifiedDate;
}