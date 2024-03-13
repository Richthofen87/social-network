package ru.skillbox.diplom.group46.social.network.api.resource.dialog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.skillbox.diplom.group46.social.network.api.dto.dialog.DialogDto;
import ru.skillbox.diplom.group46.social.network.api.dto.dialog.MessageShortDto;
import ru.skillbox.diplom.group46.social.network.api.dto.dialog.UnreadCountDto;

import java.util.UUID;

@RequestMapping("/api/v1/dialogs")
public interface DialogController {
    @PutMapping("/{id}")
    ResponseEntity<String> updateDialog(@PathVariable UUID id);

    @GetMapping
    ResponseEntity<Page<DialogDto>> getDialog(Pageable pageable);

    @GetMapping("/unread")
    ResponseEntity<UnreadCountDto> getUnreadCount();
    @GetMapping("/recipientId/{id}")
    ResponseEntity<DialogDto> getRecipientIds(@PathVariable UUID id);

    @GetMapping("/messages")
    ResponseEntity<Page<MessageShortDto>> getMessages(String recipientId, Pageable pageable);

}
