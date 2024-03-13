package ru.skillbox.diplom.group46.social.network.domain.friend;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.diplom.group46.social.network.domain.base.BaseEntity;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "friend")
@AllArgsConstructor
@NoArgsConstructor
public class Friend extends BaseEntity {

    @Column(name = "author_id" )
    private UUID authorId;

    @Column(name = "friend_id")
    private UUID friendId;

    @Column(name = "status_code")
    private String statusCode;

    @Column(name = "previous_status_code")
    private String previousStatusCode;
}
