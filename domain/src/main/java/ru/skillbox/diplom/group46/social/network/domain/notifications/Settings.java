package ru.skillbox.diplom.group46.social.network.domain.notifications;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import ru.skillbox.diplom.group46.social.network.domain.base.BaseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Settings
 *
 * @author vladimir.sazonov
 */

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = true)
public class Settings extends BaseEntity {

    @Column(name = "account_id")
    private final UUID accountId;

    @Column(name = "enable_like")
    private Boolean enableLike = true;

    @Column(name = "enable_post")
    private Boolean enablePost = true;

    @Column(name = "enable_post_comment")
    private Boolean enablePostComment = true;

    @Column(name = "enable_comment_comment")
    private Boolean enableCommentComment = true;

    @Column(name = "enable_message")
    private Boolean enableMessage = true;

    @Column(name = "enable_friend_request")
    private Boolean enableFriendRequest = true;

    @Column(name = "enable_friend_birthday")
    private Boolean enableFriendBirthday = true;

    @Column(name = "enable_send_email_message")
    private Boolean enableSendEmailMessage = true;

    @Column(name = "enable_friend_approve")
    private Boolean enableFriendApprove = true;

    @Column(name = "enable_friend_blocked")
    private Boolean enableFriendBlocked = true;

    @Column(name = "enable_friend_unblocked")
    private Boolean enableFriendUnBlocked = true;

    @Column(name = "enable_friend_subscribe")
    private Boolean enableFriendSubscribe = true;

    @Column(name = "enable_user_birthday")
    private Boolean enableUserBirthday = true;

    public Map<String, Boolean> getProperties() {
        return new HashMap<>() {{
            put("LIKE", getEnableLike());
            put("POST", getEnablePost());
            put("POST_COMMENT", getEnablePostComment());
            put("COMMENT_COMMENT", getEnableCommentComment());
            put("MESSAGE", getEnableMessage());
            put("FRIEND_REQUEST", getEnableFriendRequest());
            put("FRIEND_BIRTHDAY", getEnableFriendBirthday());
            put("SEND_EMAIL_MESSAGE", getEnableSendEmailMessage());
            put("FRIEND_APPROVE", getEnableFriendApprove());
            put("FRIEND_BLOCKED", getEnableFriendBlocked());
            put("FRIEND_UNBLOCKED", getEnableFriendUnBlocked());
            put("FRIEND_SUBSCRIBE", getEnableFriendSubscribe());
            put("USER_BIRTHDAY", getEnableUserBirthday());
        }};
    }

    public Map<String, Consumer<Boolean>> getPropertiesToSet() {
        return new HashMap<>() {{
            put("LIKE", (enable) -> setEnableLike(enable));
            put("POST", (enable) -> setEnablePost(enable));
            put("POST_COMMENT", (enable) -> setEnablePostComment(enable));
            put("COMMENT_COMMENT", (enable) -> setEnableCommentComment(enable));
            put("MESSAGE", (enable) -> setEnableMessage(enable));
            put("FRIEND_REQUEST", (enable) -> setEnableFriendRequest(enable));
            put("FRIEND_BIRTHDAY", (enable) -> setEnableFriendBirthday(enable));
            put("SEND_EMAIL_MESSAGE", (enable) -> setEnableSendEmailMessage(enable));
            put("FRIEND_APPROVE", (enable) -> setEnableFriendApprove(enable));
            put("FRIEND_BLOCKED", (enable) -> setEnableFriendBlocked(enable));
            put("FRIEND_UNBLOCKED", (enable) -> setEnableFriendUnBlocked(enable));
            put("FRIEND_SUBSCRIBE", (enable) -> setEnableFriendSubscribe(enable));
            put("USER_BIRTHDAY", (enable) -> setEnableUserBirthday(enable));
        }};
    }
}