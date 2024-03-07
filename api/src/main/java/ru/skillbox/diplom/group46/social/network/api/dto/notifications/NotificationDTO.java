package ru.skillbox.diplom.group46.social.network.api.dto.notifications;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.skillbox.diplom.group46.social.network.api.dto.base.BaseDto;
import ru.skillbox.diplom.group46.social.network.domain.notifications.NotificationType;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * NotificationDTO
 *
 * @author vladimir.sazonov
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class NotificationDTO extends BaseDto {
    private UUID authorId;
    private UUID receiverId;
    private String content;
    private NotificationType notificationType;
    private ZonedDateTime sentTime;
}