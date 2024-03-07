package ru.skillbox.diplom.group46.social.network.impl.resource.notifications;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.diplom.group46.social.network.api.dto.notifications.*;
import ru.skillbox.diplom.group46.social.network.api.resource.notifications.NotificationsController;
import ru.skillbox.diplom.group46.social.network.domain.notifications.Notification;
import ru.skillbox.diplom.group46.social.network.domain.notifications.Settings;
import ru.skillbox.diplom.group46.social.network.impl.service.notifications.NotificationsService;

/**
 * NotificationsControllerImpl
 *
 * @author vladimir.sazonov
 */

@Slf4j
@RestController
@RequiredArgsConstructor
public class NotificationsControllerImpl implements NotificationsController {

    private final NotificationsService service;

    @Override
    public ResponseEntity<Page<ContentDto>> get() {
        log.debug("Method get() started");
        return ResponseEntity.ok(service.getAllNotifications());
    }

    @Override
    public ResponseEntity<Settings> getSettings() {
        log.debug("Method getSettings() started");
        return ResponseEntity.ok(service.getSettings());
    }

    @Override
    public ResponseEntity<Boolean> updateSettings(SettingUpdateDTO dto) {
        log.debug("Method updateSettings(%s) started with param: \"%s\""
                .formatted(SettingUpdateDTO.class, dto));
        return ResponseEntity.ok(service.updateSettings(dto));
    }

    @Override
    public ResponseEntity<Boolean> setSettings(String id) {
        log.debug("Method setSettings(%s) started with param: \"%s\""
                .formatted(String.class, id));
        return ResponseEntity.ok(service.setSettings(id));
    }

    @Override
    public ResponseEntity<Boolean> setReaded() {
        log.debug("Method setReaded() started");
        return ResponseEntity.ok(service.setAllNotificationsReaded());
    }

    @Override
    public ResponseEntity<Notification> create(Notification note) {
        log.debug("Method create() started");
        return ResponseEntity.ok(service.createNotification(note));
    }

    @Override
    public ResponseEntity<Notification> addToDb(Notification notification) {
        log.debug("Method addToDb(%s) started with param: \"%s\""
                .formatted(Notification.class, notification));
        return ResponseEntity.ok(service.addNotificationToDb(notification));
    }

    @Override
    public ResponseEntity<Page<ContentDto>> getPage(Pageable pageable) {
        log.debug("Method getPage(%s) started with param: \"%s\""
                .formatted(Pageable.class, pageable));
        return ResponseEntity.ok(service.getAllSendNotifications(pageable));
    }

    @Override
    public ResponseEntity<CountDto> getCount() {
        log.debug("Method getCount() started");
        return ResponseEntity.ok(service.getSendNotificationCount());
    }
}