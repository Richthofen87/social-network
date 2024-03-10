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
@Table(name = "likes")
public class Like extends BaseEntity {

    @Column(name = "author_id")
    private UUID authorId;

    @Column(nullable = false, name = "time")
    private ZonedDateTime time;

    @Column(name = "item_id")
    private UUID itemId;

    @Column(nullable = false, name = "type")
    @Enumerated(EnumType.STRING)
    private CommentType type;

    @Column(name = "reaction_type")
    private String reactionType;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;


}
