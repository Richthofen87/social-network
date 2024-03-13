package ru.skillbox.diplom.group46.social.network.impl.mapper.dialog;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skillbox.diplom.group46.social.network.api.dto.dialog.DialogDto;
import ru.skillbox.diplom.group46.social.network.api.dto.dialog.MessageShortDto;
import ru.skillbox.diplom.group46.social.network.domain.dialog.Dialog;
import ru.skillbox.diplom.group46.social.network.domain.dialog.Message;

@Mapper
public interface MessageMapper {
    @Mapping(target = "conversationPartner1", source = "authorId")
    @Mapping(target = "conversationPartner2", source = "friendId")
    @Mapping(target = "messageText", source = "message")
    MessageShortDto messageToMessageShortDto(Message message);
}
