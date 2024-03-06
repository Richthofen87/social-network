package ru.skillbox.diplom.group46.social.network.impl.resource.account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.diplom.group46.social.network.api.dto.account.AccountDto;
import ru.skillbox.diplom.group46.social.network.api.dto.account.AccountSearchDto;
import ru.skillbox.diplom.group46.social.network.api.resource.account.AccountController;
import ru.skillbox.diplom.group46.social.network.impl.auth.service.account.AccountService;

import java.util.UUID;

/**
 * AccountControllerImpl
 *
 * @author vladimir.sazonov
 */

@Slf4j
@RestController
@RequiredArgsConstructor
public class AccountControllerImpl implements AccountController {

    private final AccountService accountService;

    @Override
    public ResponseEntity<AccountDto> get() {
        log.debug("Method get() started");
        return ResponseEntity.ok(accountService.get());
    }

    @Override
    public ResponseEntity<AccountDto> updateCurrent(AccountDto accountDto) {
        log.debug("Method updateCurrent(%s) started with param: \"%s\""
                .formatted(AccountDto.class, accountDto));
        return ResponseEntity.ok(accountService.updateCurrent(accountDto));
    }

    @Override
    public ResponseEntity<Boolean> delete() {
        log.debug("Method delete() started");
        return ResponseEntity.ok(accountService.delete());
    }

    @Override
    public ResponseEntity<AccountDto> getByEmail(String email) {
        log.debug("Method getByEmail(%s) started with param: \"%s\""
                .formatted(String.class, email));
        return ResponseEntity.ok(accountService.getByEmail(email));
    }

    @Override
    public ResponseEntity<AccountDto> updateAndGet(AccountDto accountDto) {
        log.debug("Method updateAndGet(%s) started with param: \"%s\""
                .formatted(AccountDto.class, accountDto));
        return ResponseEntity.ok(accountService.update(accountDto));
    }

    @Override
    public ResponseEntity<AccountDto> createAndGet(AccountDto accountDto) {
        log.debug("Method createAndGet(%s) started with param: \"%s\""
                .formatted(AccountDto.class, accountDto));
        return ResponseEntity.ok(accountService.create(accountDto));
    }

    @Override
    public ResponseEntity<AccountDto> getById(UUID uuid) {
        log.debug("Method getById(%s) started with param: \"%s\""
                .formatted(UUID.class, uuid));
        return ResponseEntity.ok(accountService.getById(uuid));
    }

    @Override
    public ResponseEntity<Boolean> deleteAccountById(UUID uuid) {
        log.debug("Method deleteAccountById(%s) started with param: \"%s\""
                .formatted(UUID.class, uuid));
        return ResponseEntity.ok(accountService.deleteById(uuid));
    }

    @Override
    public ResponseEntity<Page<AccountDto>> getAll(AccountSearchDto searchDto, Pageable pageable) {
        log.debug("Method getAll(%s, %s) started with params: \"%s\", \"%s\""
                .formatted(AccountSearchDto.class, Pageable.class , searchDto, pageable));
        return ResponseEntity.ok(accountService.getAll(searchDto, pageable));
    }

    @Override
    public void create(AccountDto accountDto) {
        log.debug("Method create(%s) started with param: \"%s\""
                .formatted(AccountDto.class, accountDto));
       accountService.create(accountDto);
    }

    @Override
    public AccountDto deleteById(UUID id) {
        log.debug("Method deleteAccountById(%s) started with param: \"%s\""
                .formatted(UUID.class, id));
        return accountService.deleteByIdAndGet(id);
    }

    @Override
    public void update(AccountDto accountDto) {
        log.debug("Method update(%s) started with param: \"%s\""
                .formatted(AccountDto.class, accountDto));
        accountService.update(accountDto);
    }
}