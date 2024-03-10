package ru.skillbox.diplom.group46.social.network.impl.repository.post;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group46.social.network.api.dto.post.enums.CommentTypeDto;
import ru.skillbox.diplom.group46.social.network.domain.post.Post;
import ru.skillbox.diplom.group46.social.network.impl.repository.base.BaseRepository;

import java.util.UUID;

@Repository
public interface PostRepository extends BaseRepository<Post, UUID> {
    @Override
    public default void hardDeleteById(UUID uuid) {
        deleteById(uuid);
    }

    @Override
    public default void hardDeleteAll() {
        deleteAll();
    }
}


