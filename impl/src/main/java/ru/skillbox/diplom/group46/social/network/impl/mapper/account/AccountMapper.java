package ru.skillbox.diplom.group46.social.network.impl.mapper.account;

import org.mapstruct.*;
import ru.skillbox.diplom.group46.social.network.api.dto.account.AccountDto;
import ru.skillbox.diplom.group46.social.network.domain.account.Account;
import ru.skillbox.diplom.group46.social.network.domain.account.status_code.StatusCode;

import java.time.LocalDate;
import java.time.ZoneId;

@Mapper(imports = {LocalDate.class, ZoneId.class, StatusCode.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class AccountMapper {

    public abstract Account dtoToEntity(AccountDto accountDto);
    @Mapping(target = "isDeleted", defaultValue = "false")
    public abstract AccountDto entityToDto(Account account);
    public abstract void update(@MappingTarget Account account, AccountDto accountDto);
}