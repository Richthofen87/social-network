package ru.skillbox.diplom.group46.social.network.impl.repository.post;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group46.social.network.api.dto.post.LikeReactionDto;
import ru.skillbox.diplom.group46.social.network.domain.post.Like;
import ru.skillbox.diplom.group46.social.network.domain.post.enums.CommentType;
import ru.skillbox.diplom.group46.social.network.impl.repository.base.BaseRepository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LikeRepository extends BaseRepository<Like, UUID> {
    @Override
    public default void hardDeleteById(UUID uuid) {
        deleteById(uuid);
    }

    @Override
    public default void hardDeleteAll() {
        deleteAll();
    }

    Like findByCommentIdAndAuthorIdAndTypeAndIsDeletedFalse(UUID commentId, UUID authorId, CommentType type);


    Like findByCommentIdAndAuthorId(UUID comment_id, UUID authorId);

    Like findByPostIdAndAuthorIdAndIsDeletedFalse(UUID postId, UUID authorId);

    @Query(value = "SELECT count(l) FROM likes l WHERE l.item_id = ?1 AND l.is_deleted = false", nativeQuery = true)
    Integer likeCount(@Param("itemId") UUID id);

    Like findByPostIdAndAuthorIdAndReactionType(UUID postId, UUID authorId, String reactionType);

    void deleteByCommentId(UUID commentId);

    void deleteByPostId(UUID postId);

    @Query("SELECT NEW ru.skillbox.diplom.group46.social.network.api.dto.post.LikeReactionDto(l.reactionType, COUNT(l.reactionType))" +
            " FROM Like l WHERE l.post.id = :postId AND l.isDeleted = false GROUP BY l.reactionType")
    List<LikeReactionDto> getReactionType(@Param("postId") UUID id);



}
