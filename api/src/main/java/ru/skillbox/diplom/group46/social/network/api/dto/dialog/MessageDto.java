package ru.skillbox.diplom.group46.social.network.api.dto.dialog;

import lombok.Data;
import ru.skillbox.diplom.group46.social.network.api.dto.base.BaseDto;

import java.util.Date;
import java.util.UUID;

@Data
public class MessageDto extends BaseDto {
 private Date time;
 private UUID conversationPartner1;
 private UUID conversationPartner2;
 private String messageText;
 private String readStatus;
 private UUID dialogId;
}
