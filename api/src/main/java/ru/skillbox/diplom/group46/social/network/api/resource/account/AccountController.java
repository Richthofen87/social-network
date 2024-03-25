package ru.skillbox.diplom.group46.social.network.api.resource.account;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.diplom.group46.social.network.api.dto.account.AccountDto;
import ru.skillbox.diplom.group46.social.network.api.dto.account.AccountSearchDto;
import ru.skillbox.diplom.group46.social.network.api.dto.account.AccountUpdateDto;
import ru.skillbox.diplom.group46.social.network.api.resource.base.BaseController;

import java.util.UUID;

/**
 * AccountController
 *
 * @author vladimir.sazonov
 */

@RestController
@RequestMapping("api/v1/account")
public interface AccountController extends BaseController<AccountDto, AccountSearchDto> {

    @GetMapping("/me")
    ResponseEntity<AccountDto> get();

    @PutMapping("/me")
    ResponseEntity<AccountUpdateDto> updateCurrent(@RequestBody AccountUpdateDto dto);

    @DeleteMapping("/me")
    ResponseEntity<Boolean> delete();

    @GetMapping
    ResponseEntity<AccountDto> getByEmail(@RequestParam String email);

    @PutMapping
    ResponseEntity<AccountDto> updateAndGet(@RequestBody AccountDto dto);

    @PostMapping
    ResponseEntity<AccountDto> createAndGet(@RequestBody AccountDto dto);

    @GetMapping("/{id}")
    ResponseEntity<AccountDto> getById(@PathVariable("id") UUID id);

    @DeleteMapping("/{id}")
    ResponseEntity<Boolean> deleteAccountById(@PathVariable("id") UUID id);

    @GetMapping("/search")
    ResponseEntity<Page<AccountDto>> getAll(AccountSearchDto searchDto,
                                            Pageable pageable);
}