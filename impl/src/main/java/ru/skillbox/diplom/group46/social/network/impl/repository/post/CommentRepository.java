package ru.skillbox.diplom.group46.social.network.impl.repository.post;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.diplom.group46.social.network.domain.post.Comment;
import ru.skillbox.diplom.group46.social.network.impl.repository.base.BaseRepository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends BaseRepository<Comment, UUID> {

    @Override
    public default void hardDeleteById(UUID uuid) {
        deleteById(uuid);
    }

    @Override
    public default void hardDeleteAll() {
        deleteAll();
    }

    @Query(value = "SELECT count(c) FROM comment c WHERE c.parent_id = ?1 AND c.is_deleted = false", nativeQuery = true)
    Integer commentCount(@Param("postId") UUID id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE comment SET is_deleted = true WHERE post_id = :postId", nativeQuery = true)
    void updateIsDeletedByPostId(@Param("postId") UUID postId);

    List<Comment> findByPostId(UUID postId);

    void deleteByPostId(UUID postId);



}


