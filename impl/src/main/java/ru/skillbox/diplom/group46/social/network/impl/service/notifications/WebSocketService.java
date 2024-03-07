package ru.skillbox.diplom.group46.social.network.impl.service.notifications;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group46.social.network.domain.notifications.Notification;

/**
 * WebSocketService
 *
 * @author vladimir.sazonov
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class WebSocketService {
    private final SimpMessagingTemplate messagingTemplate;
    public void sendNotification(Notification note) {
        log.debug("Method getNotificationsToSend(%s) started with param: \"%s\""
                .formatted(Notification.class, note));
        messagingTemplate.convertAndSendToUser(note.getReceiverId().toString(),
                "notifications/friends", note);
    }
}