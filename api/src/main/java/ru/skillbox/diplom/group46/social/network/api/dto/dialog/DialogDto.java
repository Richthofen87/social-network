package ru.skillbox.diplom.group46.social.network.api.dto.dialog;

import lombok.Data;
import ru.skillbox.diplom.group46.social.network.api.dto.base.BaseDto;

import java.util.Set;
import java.util.UUID;
@Data
public class DialogDto extends BaseDto {
private Integer unreadCount;
private String statusCode;
private UUID conversationPartner1;
private UUID conversationPartner2;
private Set<MessageDto> lastMessages;
}
