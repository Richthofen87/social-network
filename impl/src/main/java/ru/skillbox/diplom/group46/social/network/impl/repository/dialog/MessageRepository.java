package ru.skillbox.diplom.group46.social.network.impl.repository.dialog;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group46.social.network.domain.dialog.Message;
import ru.skillbox.diplom.group46.social.network.domain.friend.Friend;
import ru.skillbox.diplom.group46.social.network.impl.repository.base.BaseRepository;

import java.util.UUID;
@Repository
public interface MessageRepository extends BaseRepository<Message, UUID> {
    @Query(value = "SELECT count(m) FROM messages m WHERE m.friend_id = ?1 AND m.read_status = 'SENT'", nativeQuery = true)
    Integer countUnreadMessages(UUID authorId);
}
