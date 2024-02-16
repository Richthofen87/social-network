package ru.skillbox.diplom.group46.social.network.api.dto.post;

import lombok.Data;
import ru.skillbox.diplom.group46.social.network.api.dto.base.BaseDto;
import ru.skillbox.diplom.group46.social.network.api.dto.post.enums.CommentType;

@Data
public class CommentDto extends BaseDto {

    private CommentType commentType;
    private String time;
    private String timeChanged;
    private String authorId;
    private String parentId;
    private String commentText;
    private String postId;
    private Boolean isBlocked;
    private Integer likeAmount;
    private Boolean myLike;
    private Integer commentsCount;
    private String imagePath;
}