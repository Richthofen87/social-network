package ru.skillbox.diplom.group46.social.network.impl.service.notifications;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group46.social.network.api.dto.notifications.ContentDto;
import ru.skillbox.diplom.group46.social.network.api.dto.notifications.CountDto;
import ru.skillbox.diplom.group46.social.network.api.dto.notifications.SettingUpdateDTO;
import ru.skillbox.diplom.group46.social.network.domain.account.Account;
import ru.skillbox.diplom.group46.social.network.domain.notifications.Notification;
import ru.skillbox.diplom.group46.social.network.domain.notifications.NotificationStatus;
import ru.skillbox.diplom.group46.social.network.domain.notifications.Notification_;
import ru.skillbox.diplom.group46.social.network.domain.notifications.Settings;
import ru.skillbox.diplom.group46.social.network.impl.repository.notifications.NotificationRepository;
import ru.skillbox.diplom.group46.social.network.impl.repository.notifications.SettingsRepository;
import ru.skillbox.diplom.group46.social.network.impl.utils.auth.CurrentUserExtractor;
import ru.skillbox.diplom.group46.social.network.impl.utils.specification.SpecificationUtil;

import java.time.ZonedDateTime;
import java.util.Collections;
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

    private final WebSocketService webSocketService;
    private final SettingsRepository settingsRepository;
    private final NotificationRepository notificationRepository;

    public Page<ContentDto> getAllNotifications() {
        log.debug("Method getNotifications() started");
        Specification<Notification> spec = SpecificationUtil.equalValue(Notification_.receiverId, getCurrentUserId());
        return notificationRepository.findAll(spec, PageRequest.of(0, 20, Sort.unsorted()))
                .map((note) -> new ContentDto(note.getSentTime(), note));
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
                .and(SpecificationUtil.equalValue(Notification_.status, NotificationStatus.SEND));
        return notificationRepository.findAll(spec, pageable)
                .map((note) -> new ContentDto(note.getSentTime(), note));
    }

    public CountDto getSendNotificationCount() {
        log.debug("Method getSendNotificationCount() started");
        int count = notificationRepository.countAllByStatus(NotificationStatus.SEND);
        return new CountDto(ZonedDateTime.now(), count);
    }

    public void sendNotificationsToFriends(Notification note) {
        log.debug("Method getNotificationsToSend(%s) started with param: \"%s\""
                .formatted(Notification.class, note));
        getFriends(note.getAuthorId())
                .stream()
                .map(Account::getId)
                .filter(id -> {
                            Settings settings = settingsRepository.findByAccountId(id);
                            return settings.getProperties().get(note.getNotificationType().name());
                        })
                .forEach(id -> {
                    Notification notification = new Notification();
                    notification.setAuthorId(note.getAuthorId());
                    notification.setReceiverId(id);
                    notification.setContent(note.getContent());
                    notification.setNotificationType(note.getNotificationType());
                    notification.setStatus(NotificationStatus.SEND);
                    notification.setSentTime(note.getSentTime());
                    addNotificationToDb(notification);
                    webSocketService.sendNotification(notification);
                });
    }

    private UUID getCurrentUserId() {
        log.debug("Method getCurrentUserId() started");
        return CurrentUserExtractor.getCurrentUser().getId();
    }

    private List<Account> getFriends(UUID id) {
        return Collections.emptyList();
    }
}