package ru.skillbox.diplom.group46.social.network.domain.post;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.skillbox.diplom.group46.social.network.domain.base.BaseEntity;
import ru.skillbox.diplom.group46.social.network.domain.post.enums.CommentType;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "comment")
public class Comment extends BaseEntity {

    @Column(nullable = false, name = "comment_type")
    @Enumerated(EnumType.STRING)
    private CommentType commentType;

    @Column(nullable = false, name = "comment_time")
    private ZonedDateTime time;

    @Column(nullable = false, name = "time_changed")
    private ZonedDateTime timeChanged;

    @Column(nullable = false, name = "author_id")
    private UUID authorId;

    @Column(nullable = false, name = "parent_id")
    private UUID parentId;

    @Column(nullable = false, name = "comment_text")
    private String commentText;

    @Column(nullable = false, name = "post_id")
    private UUID postId;

    @Column(nullable = false, name = "is_blocked")
    private Boolean isBlocked;

    @Column(nullable = false, name = "like_amount")
    private Integer likeAmount;

    @Column(nullable = false, name = "my_like")
    private Boolean myLike;

    @Column(nullable = false, name = "comments_count")
    private Integer commentsCount;

    @Column(name = "image_path")
    private String imagePath;
}
