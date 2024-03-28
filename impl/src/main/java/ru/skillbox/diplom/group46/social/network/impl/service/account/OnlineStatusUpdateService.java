package ru.skillbox.diplom.group46.social.network.impl.service.account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.diplom.group46.social.network.api.dto.account.AccountStatusMessage;
import ru.skillbox.diplom.group46.social.network.domain.account.Account;
import ru.skillbox.diplom.group46.social.network.impl.repository.account.AccountRepository;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class OnlineStatusUpdateService {

    private final AccountRepository accountRepository;

    @Transactional
    public void changeStatus(AccountStatusMessage statusMessage) {
        Account account = accountRepository.getById(statusMessage.getUserId());
        boolean isOnline = statusMessage.isOnline();
        Long lastOnlineTimeMillis = statusMessage.getLastLoginTime();

        // актуально 24.03.2024
        if (account != null) {
            account.setIsOnline(isOnline);
            ZonedDateTime lastOnlineTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(lastOnlineTimeMillis), ZoneId.systemDefault());
            account.setLastOnlineTime(lastOnlineTime);
            log.debug("User {} is now {}", statusMessage.getUserId(), isOnline ? "online" : "offline");
        } else {
            log.warn("Account with id {} not found", statusMessage.getUserId());
        }
    }
}
