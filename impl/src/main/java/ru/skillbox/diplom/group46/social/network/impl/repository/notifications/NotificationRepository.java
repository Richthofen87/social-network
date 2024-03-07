package ru.skillbox.diplom.group46.social.network.impl.repository.notifications;

import ru.skillbox.diplom.group46.social.network.domain.notifications.Notification;
import ru.skillbox.diplom.group46.social.network.domain.notifications.NotificationStatus;
import ru.skillbox.diplom.group46.social.network.impl.repository.base.BaseRepository;

import java.util.List;
import java.util.UUID;

/**
 * NotificationRepository
 *
 * @author vladimir.sazonov
 */

public interface NotificationRepository extends BaseRepository<Notification, UUID> {

    List<Notification> findAllByReceiverId(UUID id);
    int countAllByStatus(NotificationStatus status);
}
