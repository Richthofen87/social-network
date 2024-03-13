package ru.skillbox.diplom.group46.social.network.impl.repository.dialog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group46.social.network.domain.dialog.Dialog;
import ru.skillbox.diplom.group46.social.network.domain.dialog.Message;
import ru.skillbox.diplom.group46.social.network.impl.repository.base.BaseRepository;

import java.util.UUID;

@Repository
public interface DialogRepository extends BaseRepository<Dialog, UUID> {
    @Query(value = "SELECT d FROM Dialog d WHERE d.authorId = ?1 OR d.friendId = ?1")
    Dialog getRecipientIds(UUID friendId);


}
