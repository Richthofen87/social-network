package ru.skillbox.diplom.group46.social.network.api.dto.post;

import lombok.Data;
import ru.skillbox.diplom.group46.social.network.api.dto.base.BaseDto;
import ru.skillbox.diplom.group46.social.network.api.dto.post.enums.CommentTypeDto;
import java.util.UUID;

@Data
public class LikeDto extends BaseDto {

    private UUID authorId;
    private String time;
    private UUID itemId;
    private CommentTypeDto type;
    private String reactionType;
    private UUID postId;
    private UUID commentId;
}
