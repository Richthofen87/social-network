package ru.skillbox.diplom.group46.social.network.api.dto.friend;

import lombok.Data;
import ru.skillbox.diplom.group46.social.network.api.dto.base.BaseDto;
import ru.skillbox.diplom.group46.social.network.domain.account.status_code.StatusCode;

import java.util.UUID;

@Data
public class FriendDto extends BaseDto {
    private UUID AuthorId;
    private boolean isDeleted;
    private UUID friendId;
    private StatusCode statusCode;
    private StatusCode previousStatusCode;
    private Integer rating;
}
