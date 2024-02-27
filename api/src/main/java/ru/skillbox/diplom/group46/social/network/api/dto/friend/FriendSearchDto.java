package ru.skillbox.diplom.group46.social.network.api.dto.friend;

import lombok.Data;
import ru.skillbox.diplom.group46.social.network.api.dto.base.BaseSearchDto;

import java.util.Set;

@Data
public class FriendSearchDto extends BaseSearchDto {
    private String id;
    private boolean isDeleted;
    private Set<String> friendIds;
    private String idFrom;
    private String idTo;
    private String statusCode;
    private String previousStatusCode;
    private Integer rating;
}
