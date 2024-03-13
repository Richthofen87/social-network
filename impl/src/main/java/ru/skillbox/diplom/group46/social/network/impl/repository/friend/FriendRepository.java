package ru.skillbox.diplom.group46.social.network.impl.repository.friend;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group46.social.network.domain.friend.Friend;
import ru.skillbox.diplom.group46.social.network.impl.repository.base.BaseRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;
@Repository
public interface FriendRepository extends BaseRepository<Friend, UUID> {


    @Query(value = "SELECT f FROM Friend f WHERE f.authorId = ?1 AND f.friendId = ?2")
    Friend getFriend(UUID authorId, UUID idFriend);


    @Query(value = "SELECT f.friendId FROM Friend f WHERE f.authorId = ?1 AND f.isDeleted = false AND f.statusCode = 'FRIEND'")
    List<UUID> getFriendId(UUID id);

    @Query(value = "SELECT count(*) FROM Friend f WHERE f.statusCode = 'REQUEST_TO' AND f.authorId = ?1")
    Integer getFriendCount(UUID id);

    @Query(value = "SELECT f.friendId FROM Friend f WHERE f.authorId = ?1 AND f.statusCode = ?2 AND f.isDeleted = false")
    Set<UUID> getFriendAuthor(UUID authorId, String status);

    @Query(value = "SELECT f FROM Friend f WHERE f.friendId IN ?1 AND f.statusCode = 'FRIEND' AND f.isDeleted = false")
    Set<Friend> getRecommendations(Set<UUID> authorId);

    @Query(value = "SELECT f.friendId FROM Friend f WHERE f.authorId IN ?1 AND f.statusCode = 'FRIEND' AND f.isDeleted = false")
    Set<UUID> getFriendsAuthors(Set<UUID> friendAuthor);

    @Query(value = "SELECT f.statusCode FROM Friend f WHERE f.authorId = ?1 AND f.statusCode = ?2 AND f.isDeleted = false")
    Set<String> getFriendsByStatusCode(UUID id, String status);

    @Query(value = "SELECT f.friendId FROM Friend f WHERE f.authorId IN ?1 AND f.statusCode = 'FRIEND' AND f.isDeleted = false")
    Set<UUID> getFriendsAuthor(UUID friendAuthor);

    @Query(value = "SELECT f.friendId FROM Friend f WHERE f.authorId IN ?1 AND f.statusCode = 'BLOCKED' AND f.isDeleted = false")
    Set<UUID> getBlockFriendId(UUID id);
}
