package ru.skillbox.diplom.group46.social.network.impl.resource.dialog;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.diplom.group46.social.network.api.dto.dialog.DialogDto;
import ru.skillbox.diplom.group46.social.network.api.dto.dialog.MessageShortDto;
import ru.skillbox.diplom.group46.social.network.api.dto.dialog.UnreadCountDto;
import ru.skillbox.diplom.group46.social.network.api.resource.dialog.DialogController;
import ru.skillbox.diplom.group46.social.network.impl.service.dialog.DialogService;
import ru.skillbox.diplom.group46.social.network.impl.service.friend.FriendService;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DialogControllerImpl implements DialogController {
    private final DialogService dialogService;

    @Override
    public ResponseEntity<String> updateDialog(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<Page<DialogDto>> getDialog(Pageable pageable) {
       log.info("DialogControllerImpl.getDialog() StartMethod");
       return ResponseEntity.ok(dialogService.getDialogs(pageable));
    }

    @Override
    public ResponseEntity<UnreadCountDto> getUnreadCount() {
        log.info("DialogControllerImpl.getUnreadCount() StartMethod");
        return ResponseEntity.ok(dialogService.getUnreadCount());

    }

    @Override
    public ResponseEntity<DialogDto> getRecipientIds(UUID id) {
        log.info("DialogControllerImpl.getRecipientIds() StartMethod");
        return ResponseEntity.ok(dialogService.getRecipientIds(id));
    }

    @Override
    public ResponseEntity<Page<MessageShortDto>> getMessages(String recipientId, Pageable pageable) {
        log.info("DialogControllerImpl.getMessages() StartMethod");
        return ResponseEntity.ok(dialogService.getMessages(recipientId, pageable));
    }
}
