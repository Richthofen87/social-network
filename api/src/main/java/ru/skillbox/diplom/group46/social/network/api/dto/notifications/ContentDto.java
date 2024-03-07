package ru.skillbox.diplom.group46.social.network.api.dto.notifications;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.skillbox.diplom.group46.social.network.domain.notifications.Notification;

import java.time.ZonedDateTime;

/**
 * ContentDto
 *
 * @author vladimir.sazonov
 */

@Data
@AllArgsConstructor
public class ContentDto {
    private ZonedDateTime timeStamp;
    private Notification data;
}
