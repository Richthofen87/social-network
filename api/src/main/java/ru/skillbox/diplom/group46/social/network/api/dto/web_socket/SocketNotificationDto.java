package ru.skillbox.diplom.group46.social.network.api.dto.web_socket;

import lombok.Data;
import ru.skillbox.diplom.group46.social.network.api.dto.notifications.NotificationDto;

import java.util.UUID;

@Data
public class SocketNotificationDto {

    private final String TYPE = "notification";
    private UUID recipientId;
    private NotificationDto data;
}
