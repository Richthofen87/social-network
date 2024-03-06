package ru.skillbox.diplom.group46.social.network.impl.mapper.friend;

import org.mapstruct.Mapper;


import ru.skillbox.diplom.group46.social.network.api.dto.friend.FriendDto;
import ru.skillbox.diplom.group46.social.network.domain.friend.Friend;
import java.util.Set;
import java.util.UUID;

@Mapper
public interface FriendMapper {
    FriendDto friendToFriendDto(Friend friend);
    Friend createAuthorToFriend(UUID authorId, UUID friendId);
    Set<FriendDto> SetFriendToFriendDto(Set<Friend> friendAuthor);
}
