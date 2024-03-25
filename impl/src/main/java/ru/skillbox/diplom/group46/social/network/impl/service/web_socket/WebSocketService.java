package ru.skillbox.diplom.group46.social.network.impl.service.web_socket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group46.social.network.api.dto.notifications.NotificationDto;
import ru.skillbox.diplom.group46.social.network.api.dto.web_socket.SocketNotificationDto;

import java.io.IOException;

/**
 * WebSocketService
 *
 * @author vladimir.sazonov
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class WebSocketService {

    private final WebSocketHandler handler;

    public void sendNotification(NotificationDto dto) {

        log.debug("Method getNotificationsToSend(%s) started with param: \"%s\""
                .formatted(NotificationDto.class, dto));
        try {
            SocketNotificationDto socketDto = new SocketNotificationDto();
            socketDto.setRecipientId(dto.getReceiverId());
            socketDto.setData(dto);
            handler.sendMessage(socketDto);
        }
        catch (IOException ex) {
            log.warn("Exception %s: %s, caused: %s".formatted(ex.getClass(),
                    ex.getMessage(), ex.getCause()));
        }
    }
}