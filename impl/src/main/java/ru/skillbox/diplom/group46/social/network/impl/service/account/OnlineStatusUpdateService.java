package ru.skillbox.diplom.group46.social.network.impl.service.account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.diplom.group46.social.network.api.dto.account.AccountStatusMessage;
import ru.skillbox.diplom.group46.social.network.domain.account.Account;
import ru.skillbox.diplom.group46.social.network.impl.repository.account.AccountRepository;

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
        ZonedDateTime lastOnlineTime = statusMessage.getLastLoginTime();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (account != null) {
            account.setIsOnline(isOnline);
            account.setLastOnlineTime(ZonedDateTime.from(lastOnlineTime));
            accountRepository.save(account);
            log.debug("User {} is now {}", statusMessage.getUserId(), isOnline ? "online" : "offline");
        } else {
            log.warn("Account with id {} not found", statusMessage.getUserId());
        }
    }
}
