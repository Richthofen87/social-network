package ru.skillbox.diplom.group46.social.network.impl.mapper.dialog;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skillbox.diplom.group46.social.network.api.dto.dialog.DialogDto;
import ru.skillbox.diplom.group46.social.network.domain.dialog.Dialog;

@Mapper(uses = MessageMapper.class)
public interface DialogMapper {

    @Mapping(target = "conversationPartner1", source = "authorId")
    @Mapping(target = "conversationPartner2", source = "friendId")
    DialogDto dialogToDialogDto(Dialog dialog);
}
