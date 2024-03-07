package ru.skillbox.diplom.group46.social.network.api.dto.notifications;

import lombok.Data;
import ru.skillbox.diplom.group46.social.network.domain.notifications.NotificationType;

/**
 * SettingUpdateDTO
 *
 * @author vladimir.sazonov
 */

@Data
public class SettingUpdateDTO {
    private Boolean enable;
    private NotificationType notificationType;
}