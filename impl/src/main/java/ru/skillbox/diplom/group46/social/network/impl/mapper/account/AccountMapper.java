package ru.skillbox.diplom.group46.social.network.impl.mapper.account;

import org.mapstruct.*;
import ru.skillbox.diplom.group46.social.network.api.dto.account.AccountDto;
import ru.skillbox.diplom.group46.social.network.domain.account.Account;
import ru.skillbox.diplom.group46.social.network.domain.account.status_code.StatusCode;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Mapper(imports = {LocalDate.class, ZoneId.class, StatusCode.class, ZonedDateTime.class, Instant.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class AccountMapper {

    @Mapping(target = "lastOnlineTime", expression = "java( ZonedDateTime.ofInstant(Instant.ofEpochMilli(accountDto.getLastOnlineTime()), ZoneId.systemDefault()) )")
    public abstract Account dtoToEntity(AccountDto accountDto);
    @Mapping(target = "isDeleted", defaultValue = "false")
    @Mapping(target = "lastOnlineTime", expression = "java(account.getLastOnlineTime().toInstant().toEpochMilli())")
    public abstract AccountDto entityToDto(Account account);
    @Mapping(target = "lastOnlineTime", expression = "java( ZonedDateTime.ofInstant(Instant.ofEpochMilli(accountDto.getLastOnlineTime()), ZoneId.systemDefault()) )")
    public abstract void update(@MappingTarget Account account, AccountDto accountDto);
}