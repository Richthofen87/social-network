package ru.skillbox.diplom.group46.social.network.impl.repository.notifications;

import ru.skillbox.diplom.group46.social.network.domain.notifications.Settings;
import ru.skillbox.diplom.group46.social.network.impl.repository.base.BaseRepository;

import java.util.UUID;

/**
 * SettingsRepository
 *
 * @author vladimir.sazonov
 */

public interface SettingsRepository extends BaseRepository<Settings, UUID> {

    public Settings findByAccountId(UUID id);
}
