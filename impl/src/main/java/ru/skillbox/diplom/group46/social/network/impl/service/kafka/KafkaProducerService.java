package ru.skillbox.diplom.group46.social.network.impl.service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group46.social.network.api.dto.account.AccountStatusMessage;
import ru.skillbox.diplom.group46.social.network.api.dto.notifications.NotificationDto;
import ru.skillbox.diplom.group46.social.network.domain.notifications.NotificationStatus;
import ru.skillbox.diplom.group46.social.network.domain.notifications.NotificationType;

import java.time.ZonedDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * KafkaProducerService
 *
 * @author vladimir.sazonov
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, NotificationDto> kafkaTemplateNotification;
    private final KafkaTemplate<String, String> kafkaTemplateMessage;
    private final KafkaTemplate<String, AccountStatusMessage> kafkaTemplateStatus;

    public void sendNotification(UUID authorId, UUID receiverId, String content,
                                 NotificationType notificationType) {
        NotificationDto dto = new NotificationDto(authorId, receiverId, content,
                notificationType, ZonedDateTime.now().toEpochSecond(), NotificationStatus.SEND);
        log.debug("Method sendNotification(%s, %s, %s) started with params: \"%s\", \"%s\", \"%s\""
                .formatted(UUID.class, String.class, NotificationType.class, authorId, content, notificationType));
        CompletableFuture<SendResult<String, NotificationDto>> future = kafkaTemplateNotification.send("notifications", dto);
        future.whenComplete((result, ex) -> {
            if (ex == null) log.info("Producer send %s, with offset %d"
                    .formatted(dto, result.getRecordMetadata().offset()));
            else log.warn("Exception %s: %s, caused: %s".formatted(ex.getClass(), ex.getMessage(), ex.getCause()));
        });
    }

    public void sendMessages(String message) {
        log.info("отправка сообщения в кафку: %s".formatted(message));
        CompletableFuture<SendResult<String, String>> future = kafkaTemplateMessage.send("messages", message);
        future.whenComplete((result, ex) -> {
            if (ex == null) log.info("Producer send %s, with offset %d"
                    .formatted(message, result.getRecordMetadata().offset()));
            else log.warn("Exception %s: %s, caused: %s".formatted(ex.getClass(), ex.getMessage(), ex.getCause()));
        });
    }

    public void sendAccountStatusUpdate(UUID userId, boolean isOnline) {
        AccountStatusMessage statusMessage = new AccountStatusMessage(userId, isOnline, ZonedDateTime.now());
        log.debug("Sending account status update for user {}: {}", userId, statusMessage);
        CompletableFuture<SendResult<String, AccountStatusMessage>> future = kafkaTemplateStatus.send("isOnline", statusMessage);
        future.whenComplete((result, ex) -> {
            if (ex == null) log.info("Account status update sent for user {}: {}", userId, statusMessage);
            else log.warn("Failed to send account status update for user {}: {}", userId, statusMessage, ex);
        });
    }
}
