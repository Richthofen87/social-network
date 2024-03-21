package ru.skillbox.diplom.group46.social.network.domain.notifications;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import jakarta.persistence.*;
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
@JsonAutoDetect
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Notification extends BaseEntity {

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