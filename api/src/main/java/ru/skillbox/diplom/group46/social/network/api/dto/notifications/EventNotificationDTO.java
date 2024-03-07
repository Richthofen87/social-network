package ru.skillbox.diplom.group46.social.network.api.dto.notifications;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * EventNotificationDTO
 *
 * @author vladimir.sazonov
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class EventNotificationDTO extends NotificationDTO {
    private String status;
}