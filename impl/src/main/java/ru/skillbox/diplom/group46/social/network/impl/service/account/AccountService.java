package ru.skillbox.diplom.group46.social.network.impl.service.account;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group46.social.network.api.dto.account.AccountDto;
import ru.skillbox.diplom.group46.social.network.api.dto.account.AccountSearchDto;
import ru.skillbox.diplom.group46.social.network.domain.account.Account;
import ru.skillbox.diplom.group46.social.network.impl.mapper.account.AccountMapper;
import ru.skillbox.diplom.group46.social.network.impl.repository.account.AccountRepository;
import ru.skillbox.diplom.group46.social.network.impl.repository.user.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * AccountService
 *
 * @author vladimir.sazonov
 */

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountMapper accountMapper;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public AccountDto get() {
        return new AccountDto();
    }

    public AccountDto updateCurrent(AccountDto accountDto) {
        return new AccountDto();
    }

    public Boolean delete() {
        return true;
    }

    public AccountDto getByEmail(String email) {
        return accountMapper.entityToDto(accountRepository
                .getById(userRepository.findByEmail(email).getUuid()));
    }

    public AccountDto updateAndGet(AccountDto accountDto) {
        return accountMapper.entityToDto(accountRepository.save(getAndUpdate(accountDto)));
    }

    public AccountDto createAndGet(AccountDto accountDto) {
        return accountMapper.entityToDto(accountRepository.save(accountMapper.dtoToEntity(accountDto)));
    }

    public Optional<AccountDto> deleteById(UUID uuid) {
        Account account = accountRepository.getById(uuid);
        accountRepository.delete(account);
        return Optional.of(accountMapper.entityToDto(account));
    }

    public List<AccountDto> search(AccountSearchDto accountSearchDto, Pageable pageable) {
        return null;
    }

    public AccountDto getById(UUID uuid) {
        return accountMapper.entityToDto(accountRepository.getById(uuid));
    }

    public Page<AccountDto> getAll(AccountSearchDto accountSearchDto) {

        return null;
    }

    public void create(AccountDto accountDto) {
        accountRepository.save(accountMapper.dtoToEntity(accountDto));
    }

    public void update(AccountDto accountDto) {
        getAndUpdate(accountDto);
    }

    private Account getAndUpdate(AccountDto accountDto) {
        Account account = accountMapper.dtoToEntity(accountDto);
        account = accountRepository.getById(account.getUuid());
        accountMapper.update(account, accountDto);
        return account;
    }
}