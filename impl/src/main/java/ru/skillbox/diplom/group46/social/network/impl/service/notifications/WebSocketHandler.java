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
import ru.skillbox.diplom.group46.social.network.impl.service.KafkaProducerService;


import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final Map<String, WebSocketSession> sessionUserMap = new ConcurrentHashMap<>();
    private final KafkaProducerService kafkaProducerService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessionUserMap.put((String) session.getAttributes().get("userId"), session);
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
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
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