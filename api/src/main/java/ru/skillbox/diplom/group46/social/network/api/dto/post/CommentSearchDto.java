package ru.skillbox.diplom.group46.social.network.api.dto.post;

import ru.skillbox.diplom.group46.social.network.api.dto.post.enums.CommentType;
import lombok.Data;

@Data
public class CommentSearchDto {
    private Boolean isDeleted;
    private CommentType commentType;
    private String authorId;
    private String parentId;
    private String postId;
    private String commentId;
}
