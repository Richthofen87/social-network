package ru.skillbox.diplom.group46.social.network.domain.post;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.skillbox.diplom.group46.social.network.domain.base.BaseEntity;
import ru.skillbox.diplom.group46.social.network.domain.post.enums.Type;
import ru.skillbox.diplom.group46.social.network.domain.tag.Tag;


import java.time.ZonedDateTime;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "post")
public class Post extends BaseEntity {

    @Column(nullable = false, name = "time")
    private ZonedDateTime time;

    @Column(nullable = false, name = "time_changed")
    private ZonedDateTime timeChanged;

    @Column(nullable = false, name = "title")
    private String title;

    @Column(nullable = false, name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(nullable = false, name = "post_text")
    private String postText;

    @Column(nullable = false, name = "is_blocked")
    private boolean isBlocked;

    @Column(nullable = false, name = "comments_count")
    private Integer commentsCount;

    @Column(name = "reaction_type")
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<LikeReaction> reactionType;

    @Column(name = "my_reaction")
    private String myReaction;

    @Column(name = "like_amount")
    private Integer likeAmount;

    @Column(name = "my_like")
    private boolean myLike;

    @Column(name = "image_path")
    private String imagePath;

    @Column(nullable = false, name = "publish_date")
    private ZonedDateTime publishDate;

    @ManyToMany(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinTable(
            name = "post_tag",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();
}
