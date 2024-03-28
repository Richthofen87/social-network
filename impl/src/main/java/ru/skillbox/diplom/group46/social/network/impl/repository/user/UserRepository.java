package ru.skillbox.diplom.group46.social.network.impl.repository.user;

import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group46.social.network.domain.user.User;
import ru.skillbox.diplom.group46.social.network.impl.repository.base.BaseRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * UserRepository
 *
 * @author vladimir.sazonov
 */

@Repository
public interface UserRepository extends BaseRepository<User, UUID> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    List<User> findIdByFirstNameIgnoreCase(String authorName);

}