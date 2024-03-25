package ru.skillbox.diplom.group46.social.network.impl.mapper.notifications;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skillbox.diplom.group46.social.network.api.dto.notifications.NotificationDto;
import ru.skillbox.diplom.group46.social.network.domain.notifications.Notification;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Mapper (imports = {UUID.class, ZonedDateTime.class, Instant.class, ZoneId.class})
public abstract class NotificationMapper {

    @Mapping(target = "sentTime" , expression = "java(note.getSentTime().toInstant().toEpochMilli())")
    public abstract NotificationDto entityToDto(Notification note);

    @Mapping(target = "sentTime",
            expression = "java(ZonedDateTime.ofInstant(Instant.ofEpochMilli(dto.getSentTime()), " +
                    "ZoneId.systemDefault()))")
    public abstract Notification dtoToEntity(NotificationDto dto);
}
