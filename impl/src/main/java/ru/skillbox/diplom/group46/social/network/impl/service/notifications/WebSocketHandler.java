package ru.skillbox.diplom.group46.social.network.impl.service.notifications;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.skillbox.diplom.group46.social.network.api.dto.notifications.NotificationDto;
import ru.skillbox.diplom.group46.social.network.api.dto.web_socket.SocketNotificationDto;
import ru.skillbox.diplom.group46.social.network.impl.service.kafka.KafkaProducerService;


import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final KafkaProducerService kafkaProducerService;
    private final Map<String, WebSocketSession> sessionUserMap = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String userId = (String) session.getAttributes().get("userId");
        sessionUserMap.put(userId, session);
        updateAccountStatus(UUID.fromString(userId), true);
    }

    public void sendMessage(Object object) throws IOException {
        String json = objectMapper.writeValueAsString(object);
        if (object instanceof SocketNotificationDto<?>) {
            String userId = ((SocketNotificationDto<NotificationDto>) object).getData().getAuthorId().toString();
            sessionUserMap.get(userId).sendMessage(new TextMessage(json));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String sessionId = session.getId();
        sessionUserMap.forEach((k, v) -> {
            if (v.getId().equals(sessionId)) sessionUserMap.remove(k);
        });

        String userId = (String) session.getAttributes().get("userId");
        updateAccountStatus(UUID.fromString(userId), false);
    }

    private void updateAccountStatus(UUID userId, boolean isOnline) {
        log.debug("Updating account status for user {} to {}", userId, isOnline);
        kafkaProducerService.sendAccountStatusUpdate(userId, isOnline);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        log.info("Прием сообщения из сокета: {}", message.getPayload());
        kafkaProducerService.sendMessages(message.getPayload());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error(exception.getMessage());
    }

    @Override
    public boolean supportsPartialMessages() {
        return true;
    }
}