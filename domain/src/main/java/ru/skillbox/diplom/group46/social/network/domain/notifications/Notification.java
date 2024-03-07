package ru.skillbox.diplom.group46.social.network.domain.notifications;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.skillbox.diplom.group46.social.network.domain.base.BaseEntity;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Notification
 *
 * @author vladimir.sazonov
 */

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Notification extends BaseEntity {

    public Notification(UUID authorId, String content, NotificationType notificationType) {
        this.authorId = authorId;
        this.content = content;
        this.notificationType = notificationType;
        sentTime = ZonedDateTime.now();
        status = NotificationStatus.SEND;
    }

    @Column(name = "author_id")
    private UUID authorId;

    @Column(name = "receiver_id")
    private UUID receiverId;

    @Column(name = "content")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type")
    private NotificationType notificationType;

    @Column(name = "sent_time")
    private ZonedDateTime sentTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private NotificationStatus status;
}