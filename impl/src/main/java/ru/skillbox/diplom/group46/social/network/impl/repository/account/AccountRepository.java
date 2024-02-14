package ru.skillbox.diplom.group46.social.network.impl.repository.account;

import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group46.social.network.domain.account.Account;
import ru.skillbox.diplom.group46.social.network.impl.repository.base.BaseRepository;

/**
 * AccountRepository
 *
 * @author vladimir.sazonov
 */

@Repository
public interface AccountRepository extends BaseRepository<Account> {
}