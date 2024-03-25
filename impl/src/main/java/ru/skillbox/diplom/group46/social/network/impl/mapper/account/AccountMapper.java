package ru.skillbox.diplom.group46.social.network.impl.mapper.account;

import org.mapstruct.*;
import ru.skillbox.diplom.group46.social.network.api.dto.account.AccountDto;
import ru.skillbox.diplom.group46.social.network.api.dto.account.AccountUpdateDto;
import ru.skillbox.diplom.group46.social.network.domain.account.Account;
import ru.skillbox.diplom.group46.social.network.domain.account.status_code.StatusCode;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Mapper(imports = {LocalDate.class, ZoneId.class, StatusCode.class, ZonedDateTime.class, Instant.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class AccountMapper {

    @Mapping(target = "regDate", expression = "java(ZonedDateTime.ofInstant(Instant.ofEpochMilli(dto.getRegDate()), ZoneId.systemDefault()))")
    @Mapping(target = "birthDate", expression = "java(ZonedDateTime.ofInstant(Instant.ofEpochMilli(dto.getBirthDate()), ZoneId.systemDefault()))")
    @Mapping(target = "lastOnlineTime", expression = "java(ZonedDateTime.ofInstant(Instant.ofEpochMilli(dto.getLastOnlineTime()), ZoneId.systemDefault()))")
    @Mapping(target = "deletionTimestamp", expression = "java(ZonedDateTime.ofInstant(Instant.ofEpochMilli(dto.getDeletionTimestamp()), ZoneId.systemDefault()))")
    @Mapping(target = "createdDate", expression = "java(ZonedDateTime.ofInstant(Instant.ofEpochMilli(dto.getCreatedDate()), ZoneId.systemDefault()))")
    @Mapping(target = "lastModifiedDate", expression = "java(ZonedDateTime.ofInstant(Instant.ofEpochMilli(dto.getLastModifiedDate()), ZoneId.systemDefault()))")
    public abstract Account dtoToEntity(AccountDto dto);

    @Mapping(target = "regDate", expression = "java(account.getRegDate() == null ? null : account.getRegDate().toInstant().toEpochMilli())")
    @Mapping(target = "birthDate", expression = "java(account.getBirthDate() == null ? null : account.getBirthDate().toInstant().toEpochMilli())")
    @Mapping(target = "lastOnlineTime", expression = "java(account.getLastOnlineTime() == null ? null : account.getLastOnlineTime().toInstant().toEpochMilli())")
    @Mapping(target = "deletionTimestamp", expression = "java(account.getDeletionTimestamp() == null ? null : account.getDeletionTimestamp().toInstant().toEpochMilli())")
    @Mapping(target = "createdDate", expression = "java(account.getCreatedDate() == null ? null : account.getCreatedDate().toInstant().toEpochMilli())")
    @Mapping(target = "lastModifiedDate", expression = "java(account.getLastModifiedDate() == null ? null : account.getLastModifiedDate().toInstant().toEpochMilli())")
    public abstract AccountDto entityToDto(Account account);

    @Mapping(target = "birthDate", expression = "java(account.getBirthDate() == null ? null : String.valueOf(account.getBirthDate().toInstant().toEpochMilli()))")
    public abstract AccountUpdateDto entityToUpdateDto(Account account);

    @Mapping(target = "regDate", expression = "java(ZonedDateTime.ofInstant(Instant.ofEpochMilli(dto.getRegDate()), ZoneId.systemDefault()))")
    @Mapping(target = "birthDate", expression = "java(ZonedDateTime.ofInstant(Instant.ofEpochMilli(dto.getBirthDate()), ZoneId.systemDefault()))")
    @Mapping(target = "lastOnlineTime", expression = "java(ZonedDateTime.ofInstant(Instant.ofEpochMilli(dto.getLastOnlineTime()), ZoneId.systemDefault()))")
    @Mapping(target = "deletionTimestamp", expression = "java(ZonedDateTime.ofInstant(Instant.ofEpochMilli(dto.getDeletionTimestamp()), ZoneId.systemDefault()))")
    @Mapping(target = "createdDate", expression = "java(ZonedDateTime.ofInstant(Instant.ofEpochMilli(dto.getCreatedDate()), ZoneId.systemDefault()))")
    @Mapping(target = "lastModifiedDate", expression = "java(ZonedDateTime.ofInstant(Instant.ofEpochMilli(dto.getLastModifiedDate()), ZoneId.systemDefault()))")
    public abstract void update(@MappingTarget Account account, AccountDto dto);

    @Mapping(target = "birthDate", expression = "java(dto.getBirthDate().equals(\"none\") ? account.getBirthDate() : ZonedDateTime.parse(dto.getBirthDate()))")
    public abstract void update(@MappingTarget Account account, AccountUpdateDto dto);
}