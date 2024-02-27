package ru.skillbox.diplom.group46.social.network.impl.auth.service.friend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.diplom.group46.social.network.api.dto.friend.FriendCountDto;
import ru.skillbox.diplom.group46.social.network.api.dto.friend.FriendDto;
import ru.skillbox.diplom.group46.social.network.api.dto.friend.FriendSearchDto;
import ru.skillbox.diplom.group46.social.network.domain.account.status_code.StatusCode;
import ru.skillbox.diplom.group46.social.network.domain.friend.Friend;
import ru.skillbox.diplom.group46.social.network.domain.friend.Friend_;
import ru.skillbox.diplom.group46.social.network.impl.mapper.friend.FriendMapper;
import ru.skillbox.diplom.group46.social.network.impl.repository.friend.FriendRepository;

import ru.skillbox.diplom.group46.social.network.impl.utils.auth.CurrentUserExtractor;
import ru.skillbox.diplom.group46.social.network.impl.utils.specification.SpecificationUtil;

import java.util.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final FriendMapper friendMapper;


    //Одобрение запроса на дружбу по id
    public FriendDto addFriend(UUID friendId) {
        UUID authorId = CurrentUserExtractor.getCurrentUser().getId();
        log.info("FriendService.addFriend() StartMethod");
        return updateFriend(authorId, friendId, StatusCode.FRIEND.toString(), StatusCode.FRIEND.toString());
    }

    //Сня блокировки по id
    public FriendDto unblockFriend(UUID friendId) {
        log.info("FriendService.addFriend() StartMethod");
        UUID authorId = CurrentUserExtractor.getCurrentUser().getId();
        Friend author = checkDeleted(authorId, friendId);
        Friend friend = checkDeleted(friendId, authorId);
        return updateFriend(authorId, friendId, author.getPreviousStatusCode(), friend.getPreviousStatusCode());
    }

    //Установка блокировки по id
    public FriendDto blockFriend(UUID friendId) {
        UUID authorId = CurrentUserExtractor.getCurrentUser().getId();
        log.info("FriendService.blockFriend() StartMethod");
        return updateFriend(authorId, friendId, StatusCode.BLOCKED.toString(), StatusCode.BLOCKED.toString());
    }

    //Отправка запроса на дружбу по id
    public FriendDto addFriendRequest(UUID friendId) {
        log.info("FriendService.addFriendRequest() StartMethod");
        UUID authorId = CurrentUserExtractor.getCurrentUser().getId();
        Friend author = checkDeleted(authorId, friendId);
        Friend friend = checkDeleted(friendId, authorId);
        friend = setStatusCode(StatusCode.REQUEST_FROM.toString(), friend, friendId, authorId);
        author = setStatusCode(StatusCode.REQUEST_TO.toString(), author, authorId, friendId);
        FriendDto friendDto = friendMapper.friendToFriendDto
                (friendRepository.saveAndFlush(author));
        friendRepository.saveAndFlush(friend);
        return setRating(friendDto, friendId, authorId);
    }

    //Создать подписку на пользователя
    public FriendDto subscribeToFriend(UUID friendId) {
        log.info("FriendService.subscribeToFriend() StartMethod");
        UUID authorId = CurrentUserExtractor.getCurrentUser().getId();
        Friend author = checkDeleted(authorId, friendId);
        Friend friend = checkDeleted(friendId, authorId);
        friend = setStatusCode(StatusCode.SUBSCRIBED.toString(), friend, friendId, authorId);
        friendRepository.saveAndFlush(friend);
        FriendDto friendDto = friendMapper.friendToFriendDto
                (friendRepository.saveAndFlush(
                        setStatusCode(StatusCode.SUBSCRIBED.toString(), author, authorId, friendId)));
        return setRating(friendDto, friendId, authorId);
    }

    //Поиск связей пользователя с учетом различных критериев отбора
    @Transactional(readOnly = true)
    public Page<FriendDto> getFriends(FriendSearchDto friendSearchDto, Pageable pageable) {
        UUID authorId = CurrentUserExtractor.getCurrentUser().getId();
        Specification<Friend> specification = SpecificationUtil
                .equalValueUUID(Friend_.authorId, authorId.toString())
                .and(SpecificationUtil.ContainsValue(Friend_.statusCode, friendSearchDto.getStatusCode()));
        Page<FriendDto> friendList = friendRepository.findAll(specification, pageable).map(friendMapper::friendToFriendDto);
        friendList.forEach(friendDto -> {
            setRating(friendDto, authorId, friendDto.getFriendId());
        });
        return friendList;
    }

    //Получение друзей пользователя по id
    @Transactional(readOnly = true)
    public FriendDto getFriendId(UUID id) {
        log.info("FriendService.getFriendId() StartMethod");
        UUID authorId = CurrentUserExtractor.getCurrentUser().getId();
        return friendMapper.friendToFriendDto(friendRepository.getFriend(authorId, id));
    }

    //Удаление связи между пользователями
    public void deleteFriend(UUID friendId) {
        log.info("FriendService.deleteFriend() StartMethod");
        UUID authorId = CurrentUserExtractor.getCurrentUser().getId();
        Friend author = (friendRepository.getFriend(authorId, friendId));
        Friend friend = (friendRepository.getFriend(friendId, authorId));
        author.setIsDeleted(true);
        friend.setIsDeleted(true);
        friend = setStatusCode(StatusCode.NONE.toString(), friend, friendId, authorId);
        author = setStatusCode(StatusCode.NONE.toString(), author, authorId, friendId);
        friendRepository.saveAndFlush(author);
        friendRepository.saveAndFlush(friend);
    }

    //Получение пользователей по статусу отношений
    @Transactional(readOnly = true)
    public Set<String> getFriendsByStatus(String status) {
        log.info("FriendService.getFriendsByStatus() StartMethod");
        return friendRepository.getFriendsByStatusCode(CurrentUserExtractor.getCurrentUser().getId(), status);
    }

    // Получение рекомендаций дружбы для текущего пользователя
    @Transactional(readOnly = true)
    public Set<FriendDto> getRecommendations() {
        log.info("FriendService.getRecommendations() StartMethod");
        UUID authorId = CurrentUserExtractor.getCurrentUser().getId();
        Set<UUID> friendAuthor = friendRepository.getFriendAuthor(authorId, StatusCode.FRIEND.toString());
        Set<UUID> blokedAuthor = friendRepository.getFriendAuthor(authorId, StatusCode.BLOCKED.toString());
        Set<UUID> friends = friendRepository.getFriendsAuthors(friendAuthor);
        Set<FriendDto> friendRecommend = friendMapper.SetFriendToFriendDto
                (friendRepository.getRecommendations(friends));
        for (UUID id : friendAuthor) {
            friendRecommend.removeIf(friendDto -> friendDto.getFriendId().equals(id)
                    || friendDto.getFriendId().equals(authorId)
                    || friendDto.getFriendId().equals(
                    blokedAuthor.iterator().hasNext() ? blokedAuthor.iterator().next() : null));
        }
        friendRecommend.forEach(friendDto -> setRating(friendDto, authorId, friendDto.getFriendId()));
        friendRecommend.stream().sorted(Comparator.comparingInt(FriendDto::getRating));
        return friendRecommend;
    }
    @Transactional(readOnly = true)
    //Получение списока id всех друзей текущего пользователя
    public Set<UUID> getFriendIds() {
        log.info("FriendService.getFriendIds() StartMethod");
        return friendRepository.getFriendAuthor(CurrentUserExtractor.getCurrentUser().getId(), StatusCode.FRIEND.toString());
    }
    @Transactional(readOnly = true)
    //Получение списока id всех друзей пользователя по его id
    public Set<UUID> getFriendsIds(UUID id) {
        log.info("FriendService.getFriendsIds() StartMethod");
        return friendRepository.getFriendsAuthor(id);
    }
    @Transactional(readOnly = true)
    //Получение количества запросов в друзья для текущего пользователя
    public FriendCountDto getFriendCount() {
        log.info("FriendService.getFriendCount() StartMethod");
        Integer count = friendRepository.getFriendCount(CurrentUserExtractor.getCurrentUser().getId());
        FriendCountDto countDto = new FriendCountDto();
        countDto.setCount(count);
        return countDto;
    }
    @Transactional(readOnly = true)
    //Получение заблокированных связей текущего пользователя
    public Set<UUID> getBlockFriendId() {
        log.info("FriendService.getBlockFriendId() StartMethod");
        return friendRepository.getBlockFriendId(CurrentUserExtractor.getCurrentUser().getId());
    }

    private FriendDto updateFriend(UUID authorId, UUID friendId, String statusCodeAuthor, String statusCodeFriend) {
        Friend author = checkDeleted(authorId, friendId);
        Friend friend = checkDeleted(friendId, authorId);
        friendRepository.saveAndFlush(setStatusCode(statusCodeAuthor, friend, friendId, authorId));
        FriendDto friendDto = friendMapper.friendToFriendDto(friendRepository.saveAndFlush(setStatusCode(statusCodeFriend, author, authorId, friendId)));
        return setRating(friendDto, friendId, authorId);
    }
    private Friend setStatusCode(String statusCode, Friend friend, UUID authorId, UUID idFriend) {
        String previousStatusCode = friend.getStatusCode();
        friend.setPreviousStatusCode(previousStatusCode);
        friend.setStatusCode(statusCode);
        friend.setFriendId(idFriend);
        friend.setAuthorId(authorId);
        return friend;
    }
    private Friend createFriend(UUID authorId, UUID friendId) {
        Friend friend = friendRepository.getFriend(authorId, friendId);
        if (friend == null) {
            friend = friendMapper.createAuthorToFriend(authorId, friendId);
        }
        return friend;
    }
    private FriendDto setRating(FriendDto friendDto, UUID authorId, UUID idFriend) {
        Integer rating = 0;
        List<UUID> friendAuthor = friendRepository.getFriendId(authorId);
        List<UUID> friends = friendRepository.getFriendId(idFriend);
        rating = (int) friendAuthor.stream().filter(a -> friends.contains(a)).count();
        friendDto.setRating(rating);
        return friendDto;
    }

    private Friend checkDeleted(UUID authorId, UUID friendId) {
        Friend friend = (friendRepository.getFriend(friendId, authorId));
        if (friend == null) {
            friend = createFriend(authorId, friendId);
        } else {
            friend.setIsDeleted(false);
        }
        return friend;
    }
}

