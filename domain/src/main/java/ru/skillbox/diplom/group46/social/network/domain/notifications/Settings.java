package ru.skillbox.diplom.group46.social.network.domain.notifications;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import ru.skillbox.diplom.group46.social.network.domain.base.BaseEntity;

import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Settings extends BaseEntity {

    @Column(name = "account_id")
    private UUID accountId;

    @Column(name = "enable_like")
    private Boolean enableLike;

    @Column(name = "enabl_post")
    private Boolean enablePost;

    @Column(name = "enable_post_comment")
    private Boolean enablePostComment;

    @Column(name = "enable_comment_comment")
    private Boolean enableCommentComment;

    @Column(name = "enable_message")
    private Boolean enableMessage;

    @Column(name = "enable_friend_request")
    private Boolean enableFriendRequest;

    @Column(name = "enable_friend_birthday")
    private Boolean enableFriendBirthday;

    @Column(name = "enabl_send_email_message")
    private Boolean enableSendEmailMessage;
}



