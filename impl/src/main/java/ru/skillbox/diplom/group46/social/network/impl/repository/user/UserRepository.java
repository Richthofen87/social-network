package ru.skillbox.diplom.group46.social.network.impl.repository.user;

import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group46.social.network.domain.user.User;
import ru.skillbox.diplom.group46.social.network.impl.repository.base.BaseRepository;

/**
 * UserRepository
 *
 * @author vladimir.sazonov
 */

@Repository
public interface UserRepository extends BaseRepository<User> {

    User findByEmail(String email);
}