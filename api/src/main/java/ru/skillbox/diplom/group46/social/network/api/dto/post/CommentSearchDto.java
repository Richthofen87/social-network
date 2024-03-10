package ru.skillbox.diplom.group46.social.network.api.dto.post;

import ru.skillbox.diplom.group46.social.network.api.dto.post.enums.CommentTypeDto;
import lombok.Data;

import java.util.UUID;

@Data
public class CommentSearchDto {
    private Boolean isDeleted;
    private CommentTypeDto commentType;
    private UUID authorId;
    private UUID parentId;
    private UUID postId;
    private UUID commentId;
}
