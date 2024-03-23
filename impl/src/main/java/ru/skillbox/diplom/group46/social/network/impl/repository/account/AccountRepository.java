package ru.skillbox.diplom.group46.social.network.impl.repository.account;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group46.social.network.domain.account.Account;
import ru.skillbox.diplom.group46.social.network.impl.repository.base.BaseRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * AccountRepository
 *
 * @author vladimir.sazonov
 */

@Repository
public interface AccountRepository extends BaseRepository<Account, UUID> {
    Optional<Account> findByEmail(String email);

    @Query("SELECT a FROM Account a WHERE a.isOnline = :isOnline")
    List<Account> findByIsOnline(@Param("isOnline") boolean isOnline);
}