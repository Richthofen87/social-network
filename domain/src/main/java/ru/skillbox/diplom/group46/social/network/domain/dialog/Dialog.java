package ru.skillbox.diplom.group46.social.network.domain.dialog;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.diplom.group46.social.network.domain.base.BaseEntity;
import ru.skillbox.diplom.group46.social.network.domain.post.Like;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "dialog")
@AllArgsConstructor
@NoArgsConstructor
public class Dialog extends BaseEntity {

    @Column(name = "author_id" )
    private UUID authorId;

    @Column(name = "friend_id")
    private UUID friendId;

    @Column(name = "unread_count")
    private Integer unreadCount = 0;


    @OneToMany(mappedBy = "dialog", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Set<Message> messages = new HashSet<>();
}