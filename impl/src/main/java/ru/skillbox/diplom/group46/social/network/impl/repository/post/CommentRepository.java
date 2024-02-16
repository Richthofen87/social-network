package ru.skillbox.diplom.group46.social.network.impl.repository.post;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group46.social.network.domain.post.Comment;
import ru.skillbox.diplom.group46.social.network.impl.repository.base.BaseRepository;

import java.util.UUID;

@Repository
public interface CommentRepository extends BaseRepository<Comment> {

    @Override
    public default void hardDeleteById(UUID uuid) {
        deleteById(uuid);
    }

    @Override
    public default void hardDeleteAll() {
        deleteAll();
    }

}
