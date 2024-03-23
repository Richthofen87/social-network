package ru.skillbox.diplom.group46.social.network.impl.mapper.account;

import org.mapstruct.*;
import ru.skillbox.diplom.group46.social.network.api.dto.account.AccountDto;
import ru.skillbox.diplom.group46.social.network.domain.account.Account;
import ru.skillbox.diplom.group46.social.network.domain.account.status_code.StatusCode;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;


@Mapper(imports = {LocalDate.class, ZoneId.class, StatusCode.class, ZonedDateTime.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class AccountMapper {

    public abstract Account dtoToEntity(AccountDto accountDto);

    public abstract AccountDto entityToDto(Account account);


    public abstract void update(@MappingTarget Account account, AccountDto accountDto);
}