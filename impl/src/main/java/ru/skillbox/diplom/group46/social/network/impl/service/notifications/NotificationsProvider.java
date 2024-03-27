package ru.skillbox.diplom.group46.social.network.impl.service.notifications;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group46.social.network.api.dto.notifications.NotificationDto;
import ru.skillbox.diplom.group46.social.network.domain.notifications.Notification;
import ru.skillbox.diplom.group46.social.network.domain.notifications.NotificationType;
import ru.skillbox.diplom.group46.social.network.domain.notifications.Settings;
import ru.skillbox.diplom.group46.social.network.impl.mapper.notifications.NotificationMapper;
import ru.skillbox.diplom.group46.social.network.impl.repository.notifications.SettingsRepository;
import ru.skillbox.diplom.group46.social.network.impl.service.friend.FriendService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationsProvider {

    private final FriendService service;
    private final NotificationMapper mapper;
    private final SettingsRepository repository;
    private static FriendService friendService;
    private static NotificationMapper notificationMapper;
    private static SettingsRepository settingsRepository;

    @PostConstruct
    private void initService() {
        friendService = service;
        notificationMapper = mapper;
        settingsRepository = repository;
    }

    public static List<Notification> getNotifications(NotificationDto dto) {
        log.debug("Method getNotifications(%s) started with param: \"%s\""
                .formatted(Notification.class, dto));
        NotificationType noteType = dto.getNotificationType();
        List<Notification> notes = new ArrayList<>();
        if (dto.getReceiverId() != null) {
            Notification notification = notificationMapper.dtoToEntity(dto);
            notification.setReceiverId(dto.getReceiverId());
            notes.add(notification);
        }
        else friendService.getFriendsIds(dto.getAuthorId())
                .stream()
                .filter(id -> {
                    Settings settings = settingsRepository.findByAccountId(id);
                    return settings.getProperties().get(noteType.name());
                })
                .map(id -> {
                    Notification notification = notificationMapper.dtoToEntity(dto);
                    notification.setReceiverId(id);
                    return notification;
                })
                .forEach(notes::add);
        return notes;
    }
}
