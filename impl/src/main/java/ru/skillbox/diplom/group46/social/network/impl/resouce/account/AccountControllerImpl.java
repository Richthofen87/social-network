package ru.skillbox.diplom.group46.social.network.impl.resouce.account;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.diplom.group46.social.network.api.dto.account.AccountDto;
import ru.skillbox.diplom.group46.social.network.api.dto.account.AccountSearchDto;
import ru.skillbox.diplom.group46.social.network.api.resource.account.AccountController;
import ru.skillbox.diplom.group46.social.network.impl.service.account.AccountService;

import java.util.List;
import java.util.UUID;

/**
 * AccountControllerImpl
 *
 * @author vladimir.sazonov
 */

@RestController
@RequiredArgsConstructor
public class AccountControllerImpl implements AccountController {

    private final AccountService accountService;

    @Override
    public ResponseEntity<AccountDto> get() {
        return ResponseEntity.ok(accountService.get());
    }

    @Override
    public ResponseEntity<AccountDto> updateCurrent(AccountDto accountDto) {
        return ResponseEntity.ok(accountService.updateCurrent(accountDto));
    }

    @Override
    public ResponseEntity<Boolean> delete() {
        return ResponseEntity.ok(accountService.delete());
    }

    @Override
    public ResponseEntity<AccountDto> getByEmail(String email) {
        return ResponseEntity.ok(accountService.getByEmail(email));
    }

    @Override
    public ResponseEntity<AccountDto> updateAndGet(AccountDto accountDto) {
        return ResponseEntity.ok(accountService.updateAndGet(accountDto));
    }

    @Override
    public ResponseEntity<AccountDto> createAndGet(AccountDto accountDto) {
        return ResponseEntity.ok(accountService.createAndGet(accountDto));
    }

    @Override
    public ResponseEntity<List<AccountDto>> search(AccountSearchDto searchDto, Pageable pageable) {
        return ResponseEntity.ok(accountService.search(searchDto, pageable));
    }

    @Override
    public ResponseEntity<AccountDto> getById(UUID uuid) {
        return ResponseEntity.ok(accountService.getById(uuid));
    }

    @Override
    public ResponseEntity<Boolean> deleteAccountById(UUID uuid) {
        return ResponseEntity.ok(accountService.deleteById(uuid).isPresent());
    }

    @Override
    public ResponseEntity<Page<AccountDto>> getAll(AccountSearchDto searchDto) {
        return ResponseEntity.ok(accountService.getAll(searchDto));
    }

    @Override
    public void create(AccountDto accountDto) {
       accountService.create(accountDto);
    }

    @Override
    public AccountDto deleteById(UUID id) {
        return accountService.deleteById(id).get();
    }

    @Override
    public void update(AccountDto accountDto) {
        accountService.update(accountDto);
    }
}