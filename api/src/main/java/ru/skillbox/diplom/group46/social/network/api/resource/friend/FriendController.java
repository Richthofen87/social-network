package ru.skillbox.diplom.group46.social.network.api.resource.friend;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.diplom.group46.social.network.api.dto.friend.FriendCountDto;
import ru.skillbox.diplom.group46.social.network.api.dto.friend.FriendDto;
import ru.skillbox.diplom.group46.social.network.api.dto.friend.FriendSearchDto;



import java.util.*;


@RequestMapping("/api/v1/friends")
public interface FriendController {
    @PutMapping("/{id}/approve")
    ResponseEntity<FriendDto> addFriend(@PathVariable UUID id);

    @PutMapping("/unblock/{id}")
    ResponseEntity<FriendDto> unblockFriend(@PathVariable UUID id);

    @PutMapping("/block/{id}")
    ResponseEntity<FriendDto> blockFriend(@PathVariable UUID id);

    @PostMapping("/{id}/request")
    ResponseEntity<FriendDto> sendFriendRequest(@PathVariable UUID id);

    @PostMapping("/subscribe/{id}")
    ResponseEntity<FriendDto> subscribeToFriend(@PathVariable UUID id);

    @GetMapping
    ResponseEntity<Page<FriendDto>> getFriends(FriendSearchDto friendSearchDto , Pageable pageable);
    @GetMapping("/{id}")
    ResponseEntity<FriendDto> getFriendId(@PathVariable UUID id);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteFriend(@PathVariable UUID id);
    @GetMapping("/status/{status}")
    ResponseEntity<Set<String>> getFriendsByStatus(@PathVariable String status);
    @GetMapping("/recommendations")
    ResponseEntity<Set<FriendDto>> getRecommendations();

    @GetMapping("/friendId")
    ResponseEntity<Set<UUID>> getFriendIds();

    @GetMapping("/friendId/{id}")
    ResponseEntity<Set<UUID>> getFriendsIds(@PathVariable UUID id);
    @GetMapping("/count")
    ResponseEntity<FriendCountDto> getFriendCount();

    @GetMapping("/blockFriendId")
    ResponseEntity<Set<UUID>> getBlockFriendId();













    @GetMapping("/check{ids}")
    ResponseEntity<Set<String>> checkFriendIds(@PathVariable Arrays[] ids);




}
