package ru.skillbox.diplom.group46.social.network.impl.service.account;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group46.social.network.api.dto.account.AccountDto;
import ru.skillbox.diplom.group46.social.network.api.dto.account.AccountSearchDto;
import ru.skillbox.diplom.group46.social.network.api.dto.auth.RegistrationDto;
import ru.skillbox.diplom.group46.social.network.domain.account.Account;
import ru.skillbox.diplom.group46.social.network.domain.account.Account_;
import ru.skillbox.diplom.group46.social.network.impl.mapper.account.AccountMapper;
import ru.skillbox.diplom.group46.social.network.impl.repository.account.AccountRepository;
import ru.skillbox.diplom.group46.social.network.impl.service.notifications.NotificationsService;
import ru.skillbox.diplom.group46.social.network.impl.service.role.RoleService;
import ru.skillbox.diplom.group46.social.network.impl.utils.auth.CurrentUserExtractor;
import ru.skillbox.diplom.group46.social.network.impl.utils.specification.SpecificationUtil;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * AccountService
 *
 * @author vladimir.sazonov
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final RoleService roleService;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final NotificationsService notificationsService;

    public AccountDto get() {
        log.debug("Method get started()");
        return accountMapper.entityToDto(getCurrentUserAccount());
    }

    public AccountDto updateCurrent(AccountDto accountDto) {
        log.debug("Method updateCurrent(%s) started with param: \"%s\"".formatted(AccountDto.class, accountDto));
        Account account = getCurrentUserAccount();
        accountMapper.update(account, accountDto);
        return accountMapper.entityToDto(account);
    }

    public Boolean delete() {
        log.debug("Method delete started");
        return deleteById(getCurrentUserAccount().getId());
    }

    public AccountDto getByEmail(String email) {
        log.debug("Method getByEmail(%s) started with param: \"%s\"".formatted(String.class, email));
        return accountMapper.entityToDto(accountRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Account with email: \"%s\" not found".formatted(email))));
    }

    @Transactional
    public AccountDto update(AccountDto accountDto) {
        log.debug("Method update(%s) started with param: \"%s\"".formatted(AccountDto.class, accountDto));
        Account account = accountRepository.getById(accountDto.getId());
        accountMapper.update(account, accountDto);
        return accountMapper.entityToDto(account);
    }

    @Transactional
    public AccountDto create(AccountDto accountDto) {
        log.debug("Method create(%s) started with param: \"%s\"".formatted(AccountDto.class, accountDto));
        return accountMapper.entityToDto(accountRepository.save(accountMapper.dtoToEntity(accountDto)));
    }

    @Transactional
    public AccountDto deleteByIdAndGet(UUID uuid) {
        log.debug("Method deleteByIdAndGet(%s) started with param: \"%s\"".formatted(UUID.class, uuid));
        return accountMapper.entityToDto(softDeleteById(uuid));
    }

    @Transactional
    public Boolean deleteById(UUID uuid) {
        log.debug("Method deleteById(%s) started with param: \"%s\"".formatted(UUID.class, uuid));
        Account account = softDeleteById(uuid);
        account.setDeletionTimestamp(ZonedDateTime.now());
        return true;
    }

    public AccountDto getById(UUID uuid) {
        log.debug("Method getById(%s) started with param: \"%s\"".formatted(UUID.class, uuid));
        Account account = accountRepository.getById(uuid);
        if (account == null) throw new EntityNotFoundException("Account with id: \"%s\" not found".formatted(uuid));
        return account.getIsDeleted() ? null : accountMapper.entityToDto(account);
    }

    public Page<AccountDto> getAll(AccountSearchDto searchDto, Pageable pageable) {
        log.debug("Method getAll(%s, %s) started with params: \"%s\", \"%s\""
                .formatted(AccountSearchDto.class, Pageable.class, searchDto, pageable));
        String author;
        String[] authorNames;
        UUID authorId = CurrentUserExtractor.getCurrentUserFromAuthentication().getId();
        Specification<Account> spec = SpecificationUtil
                .equalValueUUID(Account_.id, searchDto.getId())
                .and(SpecificationUtil.equalValue(Account_.isDeleted, searchDto.getIsDeleted()))
                .and(SpecificationUtil.equalValue(Account_.firstName, searchDto.getFirstName()))
                .and(SpecificationUtil.equalValue(Account_.lastName, searchDto.getLastName()))
                .and(SpecificationUtil.equalValue(Account_.city, searchDto.getCity()))
                .and(SpecificationUtil.equalValue(Account_.country, searchDto.getCountry()))
                .and(SpecificationUtil.equalValue(Account_.email, searchDto.getEmail()))
                .and(SpecificationUtil.equalValue(Account_.isBlocked, searchDto.getIsBlocked()))
                .and(SpecificationUtil.equalValue(Account_.statusCode, searchDto.getStatusCode()))
                .and(SpecificationUtil.isBetween(Account_.birthDate, searchDto.getAgeFrom(), searchDto.getAgeTo()))
                .and(SpecificationUtil.isLessValue(Account_.birthDate, searchDto.getAgeTo()))
                .and(SpecificationUtil.isGreatValue(Account_.birthDate, searchDto.getAgeFrom()))
                .and(SpecificationUtil.equalValueUUIDList(Account_.id, searchDto.getIds()))
                .and(SpecificationUtil.notEqualValue(Account_.id, authorId));
        if ((author = searchDto.getAuthor()) != null && !author.isBlank()) {
            authorNames = author.split(" ");
            if (authorNames.length == 2)
                spec = spec.and(SpecificationUtil.equalValue(Account_.firstName, authorNames[0]))
                        .and(SpecificationUtil.equalValue(Account_.lastName, authorNames[1]));
            else spec = spec.and(SpecificationUtil.equalValue(Account_.firstName, authorNames[0]));
        }
        return accountRepository.findAll(spec, pageable).map(accountMapper::entityToDto);
    }

    @Transactional
    public Account createNewAccount(RegistrationDto registrationDto) {
        log.debug("Method createNewAccount(%s) started with param: \"%s\""
                .formatted(RegistrationDto.class, registrationDto));
        String email = registrationDto.getEmail();
        if (accountRepository.findByEmail(email).isPresent())
            throw new EntityExistsException("Account with email: \"%s\" already exists".formatted(email));
        Account account = new Account();
        account.setFirstName(registrationDto.getFirstName());
        account.setLastName(registrationDto.getLastName());
        account.setEmail(email);
        account.setPassword(passwordEncoder.encode(registrationDto.getPassword1()));
        account.setCreatedDate(ZonedDateTime.now());
        account.setRegDate(ZonedDateTime.now());
        account.addRole(roleService.getUserRole());
        accountRepository.save(account);
        notificationsService.setSettings(account.getId().toString());
        return account;
    }

    private Account getCurrentUserAccount() {
        return accountRepository.getById(CurrentUserExtractor.getCurrentUserFromAuthentication().getId());
    }

    private Account softDeleteById(UUID uuid) {
        log.debug("Method softDeleteById(%s) started with param: \"%s\"".formatted(UUID.class, uuid));
        Account account = accountRepository.findById(uuid).orElseThrow(
                () -> new EntityNotFoundException("Account with id: \"%s\" not found".formatted(uuid)));
        account.setDeletionTimestamp(ZonedDateTime.now());
        accountRepository.deleteById(uuid);
        return account;
    }
}