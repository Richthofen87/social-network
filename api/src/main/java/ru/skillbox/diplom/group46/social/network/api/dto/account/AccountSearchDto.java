package ru.skillbox.diplom.group46.social.network.api.dto.account;

import lombok.*;
import ru.skillbox.diplom.group46.social.network.api.dto.base.BaseSearchDto;

import java.util.List;

/**
 * AccountSearchDto
 *
 * @author vladimir.sazonov
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AccountSearchDto extends BaseSearchDto {
    private List<String> blockedByIds;
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