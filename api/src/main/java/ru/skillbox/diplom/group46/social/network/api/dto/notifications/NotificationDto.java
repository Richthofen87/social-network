package ru.skillbox.diplom.group46.social.network.api.dto.notifications;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skillbox.diplom.group46.social.network.api.dto.base.BaseDto;
import ru.skillbox.diplom.group46.social.network.domain.notifications.NotificationStatus;
import ru.skillbox.diplom.group46.social.network.domain.notifications.NotificationType;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * NotificationDto
 *
 * @author vladimir.sazonov
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto extends BaseDto {

    private UUID authorId;
    private UUID receiverId;
    private String content;
    private NotificationType notificationType;
    private Long sentTime;
    private NotificationStatus status;
}