package ru.skillbox.diplom.group46.social.network.domain.post;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.skillbox.diplom.group46.social.network.domain.base.BaseAuditedEntity;
import ru.skillbox.diplom.group46.social.network.domain.post.enums.Type;
import ru.skillbox.diplom.group46.social.network.domain.tag.Tag;
import java.time.ZonedDateTime;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "post")
public class Post extends BaseAuditedEntity {

    @Column(nullable = false, name = "time")
    private ZonedDateTime time;

    @Column(nullable = false, name = "time_changed")
    private ZonedDateTime timeChanged;

    @Column(name = "author_id")
    private UUID authorId;

    @Column(nullable = false, name = "title")
    private String title;

    @Column(nullable = true, name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(nullable = false, name = "post_text")
    private String postText;

    @Column(nullable = false, name = "is_blocked")
    private Boolean isBlocked;

    @Column(name = "my_reaction")
    private String myReaction;

    @Column(name = "my_like")
    private Boolean myLike;

    @Column(name = "with_friends")
    private Boolean withFriends;

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
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Set<Like> likes = new HashSet<>();

}
