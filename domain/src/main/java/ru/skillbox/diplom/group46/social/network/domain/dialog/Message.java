package ru.skillbox.diplom.group46.social.network.domain.dialog;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.diplom.group46.social.network.domain.base.BaseEntity;
import ru.skillbox.diplom.group46.social.network.domain.post.Post;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "messages")
@AllArgsConstructor
@NoArgsConstructor
public class Message extends BaseEntity {

    @Column(name = "author_id" )
    private UUID authorId;

    @Column(name = "friend_id")
    private UUID friendId;

    @Column(nullable = false, name = "time")
    private ZonedDateTime time;

    @Column(name = "message")
    private String message;

    @Column(name = "read_status")
    private String readStatus;

    @ManyToOne
    @JoinColumn(name = "dialog_id")
    private Dialog dialog;
}