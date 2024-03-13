package ru.skillbox.diplom.group46.social.network.impl.service.dialog;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.diplom.group46.social.network.api.dto.dialog.DialogDto;
import ru.skillbox.diplom.group46.social.network.api.dto.dialog.MessageShortDto;
import ru.skillbox.diplom.group46.social.network.api.dto.dialog.UnreadCountDto;
import ru.skillbox.diplom.group46.social.network.domain.dialog.Dialog;
import ru.skillbox.diplom.group46.social.network.domain.dialog.Dialog_;

import ru.skillbox.diplom.group46.social.network.domain.dialog.Message;
import ru.skillbox.diplom.group46.social.network.domain.dialog.Message_;
import ru.skillbox.diplom.group46.social.network.impl.mapper.dialog.DialogMapper;
import ru.skillbox.diplom.group46.social.network.impl.mapper.dialog.MessageMapper;
import ru.skillbox.diplom.group46.social.network.impl.repository.dialog.DialogRepository;
import ru.skillbox.diplom.group46.social.network.impl.repository.dialog.MessageRepository;
import ru.skillbox.diplom.group46.social.network.impl.utils.auth.CurrentUserExtractor;
import ru.skillbox.diplom.group46.social.network.impl.utils.specification.SpecificationUtil;


import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DialogService {

    private final DialogRepository dialogRepository;
    private final DialogMapper dialogMapper;
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    public DialogDto getRecipientIds(UUID friendId) {
        log.info("DialogService.getRecipientIds() StartMethod");
        UUID authorId = CurrentUserExtractor.getCurrentUser().getId();
        DialogDto dialogDto = dialogMapper.dialogToDialogDto(dialogRepository.getRecipientIds(friendId));
        if(dialogDto != null) {
            return dialogDto;
        }
        Dialog dialog = new Dialog();
        dialog.setAuthorId(authorId);
        dialog.setFriendId(friendId);
        dialogRepository.save(dialog);
        return dialogMapper.dialogToDialogDto(dialog);
    }

    public Page<DialogDto> getDialogs(Pageable pageable) {
        log.info("DialogService.getDialogs() StartMethod");
        UUID authorId = CurrentUserExtractor.getCurrentUser().getId();
        Specification<Dialog> specification = SpecificationUtil
                .equalValueUUID(Dialog_.authorId, authorId).or(SpecificationUtil.equalValueUUID(Dialog_.friendId, authorId));


        Page<DialogDto> dialogs = dialogRepository.findAll(specification, pageable).map(dialogMapper::dialogToDialogDto);
        return dialogs;
    }

    public Page<MessageShortDto> getMessages(String recipientId, Pageable pageable) {
        log.info("DialogService.getMessages() StartMethod");
        UUID authorId = CurrentUserExtractor.getCurrentUser().getId();
        Specification<Message> specification = SpecificationUtil
                .equalValueUUID(Message_.authorId, authorId).or(SpecificationUtil.equalValueUUID(Message_.friendId, authorId));
        Page<MessageShortDto> messages = messageRepository.findAll(specification, pageable).map(messageMapper::messageToMessageShortDto);
        return messages;
    }

    public UnreadCountDto getUnreadCount() {
        log.info("DialogService.getUnreadCount() StartMethod");
        UUID authorId = CurrentUserExtractor.getCurrentUser().getId();
       UnreadCountDto unreadCountDto = new UnreadCountDto();
       Integer count = messageRepository.countUnreadMessages(authorId);
        unreadCountDto.setCount(count);
        return unreadCountDto;
    }
}


