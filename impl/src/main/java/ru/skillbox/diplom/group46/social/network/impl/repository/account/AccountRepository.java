package ru.skillbox.diplom.group46.social.network.impl.repository.account;

import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group46.social.network.domain.account.Account;
import ru.skillbox.diplom.group46.social.network.impl.repository.base.BaseRepository;

import java.util.UUID;

/**
 * AccountRepository
 *
 * @author vladimir.sazonov
 */

@Repository
public interface AccountRepository extends BaseRepository<Account> {
    @Override
    public default void hardDeleteById(UUID uuid) {
        deleteById(uuid);
    }

    @Override
    public default void hardDeleteAll() {
        deleteAll();
    }
}