package ru.skillbox.diplom.group46.social.network.api.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.diplom.group46.social.network.api.dto.base.BaseSearchDto;

import java.util.List;

/**
 * AccountSearchDto
 *
 * @author vladimir.sazonov
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountSearchDto extends BaseSearchDto {
    private List<String> blockedByIds;
    private String author;
    private String firstName;
    private String lastName;
    private String city;
    private String country;
    private String email;
    private Boolean isBlocked;
    private String statusCode;
    private Integer ageFrom;
    private Integer ageTo;
    private Boolean showFriends;
}