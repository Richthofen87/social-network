package ru.skillbox.diplom.group46.social.network.impl.service.notifications;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group46.social.network.api.dto.notifications.NotificationDto;
import ru.skillbox.diplom.group46.social.network.impl.service.web_socket.WebSocketService;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationsSender {

    @Value("${microservices.enableMsNotifications}")
    private Boolean enableMsNotifications;
    private final WebSocketService webSocketService;

    @KafkaListener(topics = "notifications-2", groupId = "notesGroup-1",
            containerFactory = "kafkaNotificationDtoListenerContainerFactory")
    protected void consume(NotificationDto dto) {
        log.info("Method consume({}) started with param: \"{}\"",
                NotificationDto.class, dto);
        if (!enableMsNotifications) return;
        webSocketService.sendNotification(dto);
    }
}
