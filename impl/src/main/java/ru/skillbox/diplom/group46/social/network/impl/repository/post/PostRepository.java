package ru.skillbox.diplom.group46.social.network.impl.repository.post;

import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group46.social.network.domain.post.Post;
import ru.skillbox.diplom.group46.social.network.domain.post.enums.Type;
import ru.skillbox.diplom.group46.social.network.impl.repository.base.BaseRepository;

import java.util.List;
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

    List<Post> findByType(Type type);

    Post findByIdAndAuthorId(UUID id, UUID authorId);
}


