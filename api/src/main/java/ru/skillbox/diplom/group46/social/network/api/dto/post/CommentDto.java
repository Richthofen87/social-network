package ru.skillbox.diplom.group46.social.network.api.dto.post;

import lombok.Data;
import ru.skillbox.diplom.group46.social.network.api.dto.base.BaseDto;
import ru.skillbox.diplom.group46.social.network.api.dto.post.enums.CommentTypeDto;
import java.util.UUID;

@Data
public class CommentDto extends BaseDto {

    private CommentTypeDto commentType;
    private String time;
    private String timeChanged;
    private UUID authorId;
    private UUID parentId;
    private String commentText;
    private UUID postId;
    private Boolean isBlocked;
    private Integer likeAmount;
    private Boolean myLike;
    private Integer commentsCount;
    private String imagePath;
}