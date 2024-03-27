package ru.skillbox.diplom.group46.social.network.impl.service.notifications;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group46.social.network.api.dto.notifications.ContentDto;
import ru.skillbox.diplom.group46.social.network.api.dto.notifications.NotificationDto;
import ru.skillbox.diplom.group46.social.network.api.dto.notifications.PartCountDto;
import ru.skillbox.diplom.group46.social.network.api.dto.notifications.SettingUpdateDTO;
import ru.skillbox.diplom.group46.social.network.domain.notifications.*;
import ru.skillbox.diplom.group46.social.network.impl.mapper.notifications.NotificationMapper;
import ru.skillbox.diplom.group46.social.network.impl.repository.notifications.NotificationRepository;
import ru.skillbox.diplom.group46.social.network.impl.repository.notifications.SettingsRepository;
import ru.skillbox.diplom.group46.social.network.impl.service.web_socket.WebSocketService;
import ru.skillbox.diplom.group46.social.network.impl.utils.auth.CurrentUserExtractor;
import ru.skillbox.diplom.group46.social.network.impl.utils.specification.SpecificationUtil;

import java.util.List;
import java.util.UUID;

/**
 * NotificationsService
 *
 * @author vladimir.sazonov
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationsService {

    @Value("${microservices.enableMsNotifications}")
    private Boolean enableMsNotifications;
    private final WebSocketService webSocketService;
    private final SettingsRepository settingsRepository;
    private final NotificationMapper notificationMapper;
    private final NotificationRepository notificationRepository;

    public Page<ContentDto> getAllNotifications() {
        log.debug("Method getNotifications() started");
        Specification<Notification> spec = SpecificationUtil.equalValue(Notification_.receiverId, getCurrentUserId());
        return notificationRepository.findAll(spec, PageRequest.of(0, 20, Sort.unsorted()))
                .map((note) -> new ContentDto(note.getSentTime().toEpochSecond(), notificationMapper.entityToDto(note)));
    }

    public Notification addNotificationToDb(Notification notification) {
        log.debug("Method addNotificationToDb() started");
        return notificationRepository.save(notification);
    }

    @Transactional
    public Boolean setSettings(String id) {
        log.debug("Method setSettings(%s) started with param: \"%s\""
                .formatted(String.class, id));
        Settings settings = new Settings(UUID.fromString(id));
        settingsRepository.save(settings);
        return settingsRepository.findById(settings.getId()).isPresent();
    }

    public Settings getSettings() {
        log.debug("Method getSettings() started");
        return settingsRepository.findByAccountId(getCurrentUserId());
    }

    @Transactional
    public Boolean setAllNotificationsReaded() {
        log.debug("Method setAllNotificationsReaded() started");
        notificationRepository.findAllByReceiverId(getCurrentUserId())
                .forEach((note) -> note.setStatus(NotificationStatus.READED));
        return true;
    }

    @Transactional
    public Boolean updateSettings(SettingUpdateDTO dto) {
        log.debug("Method updateSettings(%s) started with param: \"%s\""
                .formatted(SettingUpdateDTO.class, dto));
        Settings settings = settingsRepository.findByAccountId(getCurrentUserId());
        String type = dto.getNotificationType().name();
        settings.getPropertiesToSet().get(type).accept(dto.getEnable());
        return true;
    }

    public Notification createNotification(Notification note) {
        log.debug("Method createNotification(%s) started with param: \"%s\""
                .formatted(Notification.class, note));
        note.setAuthorId(getCurrentUserId());
        note.setReceiverId(UUID.randomUUID());
        return notificationRepository.save(note);
    }

    public Page<ContentDto> getAllSendNotifications(Pageable pageable) {
        log.debug("Method getAllSendNotifications(%s) started with param: \"%s\""
                .formatted(Pageable.class, pageable));
        Specification<Notification> spec = SpecificationUtil
                .equalValue(Notification_.receiverId, getCurrentUserId())
                .and(SpecificationUtil.equalValue(Notification_.status, NotificationStatus.SEND))
                .and(SpecificationUtil.equalValue(Notification_.isDeleted, false));
        return notificationRepository.findAll(spec, pageable)
                .map((note) -> new ContentDto(note.getSentTime().toEpochSecond(), notificationMapper.entityToDto(note)));
    }

    public PartCountDto getSendNotificationCount() {
        log.debug("Method getSendNotificationCount() started");
        UUID authorId = CurrentUserExtractor.getCurrentUserFromAuthentication().getId();
        int count = notificationRepository
                .countAllByReceiverIdAndStatusAndIsDeleted(authorId, NotificationStatus.SEND, false);
        PartCountDto partCountDto = new PartCountDto();
        partCountDto.setCount(count);
        return partCountDto;
    }

    @KafkaListener(topics = "notifications-1", groupId = "notesGroup-1",
            containerFactory = "kafkaNotificationDtoListenerContainerFactory")
    protected void consume(NotificationDto dto) {
        log.info("Method consume(%s) started with param: \"%s\""
                .formatted(NotificationDto.class, dto));
        if (enableMsNotifications) return;
        sendNotificationsToFriends(dto);
    }

    public void sendNotificationsToFriends(NotificationDto note) {
        log.debug("Method getNotificationsToSend(%s) started with param: \"%s\""
                .formatted(Notification.class, note));
        NotificationsProvider.getNotifications(note)
                .forEach(n -> {
                    notificationRepository.save(n);
                    webSocketService.sendNotification(notificationMapper.entityToDto(n));
                });
    }

    public boolean softDeleteFriendRequests(UUID authorId, UUID friendId) {
        List<Notification> notes = notificationRepository
                .findAllByAuthorIdAndReceiverIdAndNotificationType(authorId, friendId, NotificationType.FRIEND_REQUEST);
        notificationRepository.deleteAll(notes);
        Specification<Notification> spec = SpecificationUtil
                .equalValue(Notification_.receiverId, friendId)
                .and(SpecificationUtil.equalValue(Notification_.authorId, authorId))
                .and(SpecificationUtil.equalValue(Notification_.notificationType, NotificationType.FRIEND_REQUEST));
        return notificationRepository.findAll(spec).isEmpty();
    }

    private UUID getCurrentUserId() {
        log.debug("Method getCurrentUserId() started");
        return CurrentUserExtractor.getCurrentUserFromAuthentication().getId();
    }
}