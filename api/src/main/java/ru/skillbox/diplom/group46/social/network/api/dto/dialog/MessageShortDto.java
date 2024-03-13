package ru.skillbox.diplom.group46.social.network.api.dto.dialog;

import lombok.Data;
import ru.skillbox.diplom.group46.social.network.api.dto.base.BaseDto;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class MessageShortDto extends BaseDto {
 private ZonedDateTime time;
 private UUID conversationPartner1;
 private UUID conversationPartner2;
 private String messageText;
}
