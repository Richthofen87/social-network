package ru.skillbox.diplom.group46.social.network.impl.repository.post;

import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group46.social.network.domain.post.Comment;
import ru.skillbox.diplom.group46.social.network.impl.repository.base.BaseRepository;

import java.util.UUID;

@Repository
public interface CommentRepository extends BaseRepository<Comment, UUID> {
}
