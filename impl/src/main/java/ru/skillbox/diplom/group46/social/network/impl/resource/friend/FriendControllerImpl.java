package ru.skillbox.diplom.group46.social.network.impl.resource.friend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.diplom.group46.social.network.api.dto.friend.FriendCountDto;
import ru.skillbox.diplom.group46.social.network.api.dto.friend.FriendDto;
import ru.skillbox.diplom.group46.social.network.api.dto.friend.FriendSearchDto;
import ru.skillbox.diplom.group46.social.network.api.resource.friend.FriendController;
import ru.skillbox.diplom.group46.social.network.impl.auth.service.friend.FriendService;

import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FriendControllerImpl implements FriendController {
    private final FriendService friendService;

    @Override
    //Одобрение запроса на дружбу по id
    public ResponseEntity<FriendDto> addFriend(UUID id) {
        log.info("FriendControllerImpl.addFriend() StartMethod");
        return ResponseEntity.ok(friendService.addFriend(id));
    }

    @Override
    //Удаление блокировки по id
    public ResponseEntity<FriendDto> unblockFriend(UUID id) {
        log.info("FriendControllerImpl.unblockFriend() StartMethod");
        return ResponseEntity.ok(friendService.unblockFriend(id));
    }

    @Override
    //Установка блокировки по id
    public ResponseEntity<FriendDto> blockFriend(UUID id) {
        log.info("FriendControllerImpl.blockFriend() StartMethod");
        return ResponseEntity.ok(friendService.blockFriend(id));
    }

    @Override
    //Отправка запроса на дружбу по id
    public ResponseEntity<FriendDto> sendFriendRequest(UUID id) {
        log.info("FriendControllerImpl.sendFriendRequest() StartMethod");
        return ResponseEntity.ok(friendService.addFriendRequest(id));
    }

    @Override
    //Установка подписки по id
    public ResponseEntity<FriendDto> subscribeToFriend(UUID id) {
        log.info("FriendControllerImpl.subscribeToFriend() StartMethod");
        return ResponseEntity.ok(friendService.subscribeToFriend(id));
    }

    @Override
    //Получает связи пользователя по входящим парамтрам отбора
    public ResponseEntity<Page<FriendDto>> getFriends(FriendSearchDto friendSearchDto, Pageable pageable) {
        log.info("FriendControllerImpl.getFriends() StartMethod");
        return ResponseEntity.ok(friendService.getFriends(friendSearchDto, pageable));
    }

    @Override
    //Получение друзей пользователя по id
    public ResponseEntity<FriendDto> getFriendId(UUID id) {
        log.info("FriendControllerImpl.getFriendId() StartMethod");
        return ResponseEntity.ok(friendService.getFriendId(id));
    }

    @Override
    //Удаление связи между пользователями
    public ResponseEntity<Void> deleteFriend(UUID id) {
        log.info("FriendControllerImpl.deleteFriend() StartMethod");
        friendService.deleteFriend(id);
        return ResponseEntity.ok().build();
    }

    @Override
    //Получение пользователей по статусу отношений
    public ResponseEntity<Set<String>> getFriendsByStatus(String status) {
        log.info("FriendControllerImpl.getFriendsByStatus() StartMethod");
        return ResponseEntity.ok(friendService.getFriendsByStatus(status));
    }

    @Override
    // Получение рекомендаций дружбы для текущего пользователя
    public ResponseEntity<Set<FriendDto>> getRecommendations() {
        log.info("FriendControllerImpl.getRecommendations() StartMethod");
        return ResponseEntity.ok(friendService.getRecommendations());
    }

    //Получение списока id всех друзей текущего пользователя
    @Override
    public ResponseEntity<Set<UUID>> getFriendIds() {
        log.info("FriendControllerImpl.getFriendIds() StartMethod");
        return ResponseEntity.ok(friendService.getFriendIds());
    }

    //Получение списока id всех друзей пользователя по его id
    @Override
    public ResponseEntity<Set<UUID>> getFriendsIds(UUID id) {
        log.info("FriendControllerImpl.getFriendIds() StartMethod");
        return ResponseEntity.ok(friendService.getFriendsIds(id));
    }

    @Override
    //Получение количества запросов в друзья для текущего пользователя
    public ResponseEntity<FriendCountDto> getFriendCount() {
        log.info("FriendControllerImpl.getFriendCount() StartMethod");
        return ResponseEntity.ok(friendService.getFriendCount());
    }

    //TODO  Непонятно
    @Override
    //Получение всех связей текущего пользователя
    public ResponseEntity<Set<String>> checkFriendIds(Arrays[] ids) {
        log.info("FriendControllerImpl.checkFriendIds() StartMethod");
        return null;
    }

    @Override
    //Получение заблокированных связей текущего пользователя
    public ResponseEntity<Set<UUID>> getBlockFriendId() {
        log.info("FriendControllerImpl.getBlockFriendId() StartMethod");
        return ResponseEntity.ok(friendService.getBlockFriendId());
    }


}
