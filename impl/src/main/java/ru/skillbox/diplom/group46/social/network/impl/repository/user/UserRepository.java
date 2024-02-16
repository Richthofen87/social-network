package ru.skillbox.diplom.group46.social.network.impl.repository.user;

import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group46.social.network.domain.user.User;
import ru.skillbox.diplom.group46.social.network.impl.repository.base.BaseRepository;

import java.util.UUID;

/**
 * UserRepository
 *
 * @author vladimir.sazonov
 */

@Repository
public interface UserRepository extends BaseRepository<User, UUID> {

    User findByEmail(String email);
    @Override
    public default void hardDeleteById(UUID uuid) {
        deleteById(uuid);
    }

    @Override
    public default void hardDeleteAll() {
        deleteAll();
    }
}