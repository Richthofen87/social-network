package ru.skillbox.diplom.group46.social.network.impl.service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.skillbox.diplom.group46.social.network.api.dto.account.AccountStatusMessage;
import ru.skillbox.diplom.group46.social.network.impl.service.account.OnlineStatusUpdateService;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaStatusListener {

    private final OnlineStatusUpdateService onlineStatusUpdateService;

    @KafkaListener(topics = "isOnline", groupId = "notesGroup-1",
            containerFactory = "kafkaAccountStatusMessageListenerContainerFactory")
    public void consumerStatus(AccountStatusMessage statusMessage) {
        try {
            log.debug("Method consumerStatus started with params: {}", statusMessage);
            onlineStatusUpdateService.changeStatus(statusMessage);
        } catch (Exception e) {
            log.error("Error processing account status update message", e);
        }
    }
}
