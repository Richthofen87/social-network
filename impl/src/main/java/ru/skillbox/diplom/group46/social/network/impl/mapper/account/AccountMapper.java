package ru.skillbox.diplom.group46.social.network.impl.mapper.account;

import org.mapstruct.*;
import ru.skillbox.diplom.group46.social.network.api.dto.account.AccountDto;
import ru.skillbox.diplom.group46.social.network.domain.account.Account;
import ru.skillbox.diplom.group46.social.network.domain.account.status_code.StatusCode;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(imports = {LocalDate.class, ZoneId.class, StatusCode.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class AccountMapper {

    private final String DATE_FORMAT = "yyyy-MM-dd'T'hh:mm:ss.SSSXXX";

    @Mapping(target = "birthDate", expression = "java(this.parse(accountDto, account, \"birthDate\", false))")
    @Mapping(target = "deletionTimestamp", expression = "java(this.parse(accountDto, account, \"deletionTimestamp\", false))")
    @Mapping(target = "lastOnlineTime", expression = "java(this.parse(accountDto, account, \"lastOnlineTime\", false))")
    @Mapping(target = "regDate", expression = "java(this.parse(accountDto, account, \"regDate\", false))")
    @Mapping(target = "createdDate", expression = "java(this.parse(accountDto, account, \"createdDate\", false))")
    public abstract Account dtoToEntity(AccountDto accountDto);

    @Mapping(target = "birthDate", source = "birthDate", dateFormat = DATE_FORMAT)
    @Mapping(target = "deletionTimestamp", source = "deletionTimestamp", dateFormat = DATE_FORMAT)
    @Mapping(target = "lastOnlineTime", source = "lastOnlineTime", dateFormat = DATE_FORMAT)
    @Mapping(target = "regDate", source = "lastOnlineTime", dateFormat = DATE_FORMAT)
    @Mapping(target = "createdDate", source = "lastOnlineTime", dateFormat = DATE_FORMAT)
    public abstract AccountDto entityToDto(Account account);

    @Mapping(target = "birthDate", expression = "java(this.parse(accountDto, account, \"birthDate\", true))")
    @Mapping(target = "deletionTimestamp", expression = "java(this.parse(accountDto, account, \"deletionTimestamp\", true))")
    @Mapping(target = "lastOnlineTime", expression = "java(this.parse(accountDto, account, \"lastOnlineTime\", true))")
    @Mapping(target = "regDate", expression = "java(this.parse(accountDto, account, \"regDate\", true))")
    @Mapping(target = "createdDate", expression = "java(this.parse(accountDto, account, \"createdDate\", true))")
    public abstract void update(@MappingTarget Account account, AccountDto accountDto);

    ZonedDateTime parse(AccountDto accountDto, Account account, String parameter, boolean update) {
        String date = "";
        switch (parameter) {
            case "getBirthDate" -> {
                if ((date = accountDto.getBirthDate()) == null && update)
                    return account.getBirthDate();
            }
            case "deletionTimestamp" -> {
                if ((date = accountDto.getDeletionTimestamp()) == null && update)
                    return account.getDeletionTimestamp();
            }
            case "lastOnlineTime" -> {
                if ((date = accountDto.getLastOnlineTime()) == null && update)
                    return account.getLastOnlineTime();
            }
            case "regDate" -> {
                if ((date = accountDto.getRegDate()) == null && update)
                    return account.getRegDate();
            }
            case "createdDate" -> {
                if ((date = accountDto.getCreatedDate()) == null && update)
                    return account.getCreatedDate();
            }
        }
        return date == null || date.isBlank() ? null : ZonedDateTime.parse(date, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}