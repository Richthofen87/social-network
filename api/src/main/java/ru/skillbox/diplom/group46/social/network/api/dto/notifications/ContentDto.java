package ru.skillbox.diplom.group46.social.network.api.dto.notifications;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

/**
 * ContentDto
 *
 * @author vladimir.sazonov
 */

@Data
@AllArgsConstructor
public class ContentDto {
    private Long timeStamp;
    private NotificationDto data;
}
