package ru.skillbox.diplom.group46.social.network.api.dto.account;

import lombok.Data;

@Data
public class AccountUpdateDto {

    private String firstName;
    private String lastName;
    private String profileCover;
    private String phone;
    private String photo;
    private String about;
    private String city;
    private String country;
    private String emojiStatus;
    private String birthDate;
}
