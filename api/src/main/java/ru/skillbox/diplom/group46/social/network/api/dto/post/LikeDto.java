package ru.skillbox.diplom.group46.social.network.api.dto.post;

import lombok.Data;
import ru.skillbox.diplom.group46.social.network.api.dto.base.BaseDto;
import ru.skillbox.diplom.group46.social.network.api.dto.post.enums.LikeType;

@Data
public class LikeDto extends BaseDto {

    private String authorId;
    private String time;
    private String itemId;
    private LikeType type;
    private ReactionTypeDto reactionType;
}
