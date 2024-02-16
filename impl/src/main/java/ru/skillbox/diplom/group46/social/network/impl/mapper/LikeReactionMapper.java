package ru.skillbox.diplom.group46.social.network.impl.mapper;

import org.mapstruct.Mapper;
import ru.skillbox.diplom.group46.social.network.api.dto.post.LikeReactionDto;
import ru.skillbox.diplom.group46.social.network.domain.post.LikeReaction;

@Mapper
public interface LikeReactionMapper {
    LikeReaction likeReactionDtoToLikeReactionEntity(LikeReactionDto likeReactionDto);

    LikeReactionDto LikeReactionEntityTolikeReactionDto(LikeReaction likeReaction);
}
