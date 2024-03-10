package ru.skillbox.diplom.group46.social.network.impl.mapper.post;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.skillbox.diplom.group46.social.network.api.dto.post.LikeDto;
import ru.skillbox.diplom.group46.social.network.domain.post.Like;
import java.time.ZonedDateTime;
import java.util.UUID;


@Mapper(imports = {ZonedDateTime.class, UUID.class})
public interface LikeMapper {


    LikeDto likeToLikeDto(Like like);

    @Mapping(target = "isDeleted", defaultValue = "false")
    @Mapping(target = "authorId", source = "likeDto.authorId")
    @Mapping(target = "itemId", source = "likeDto.itemId")
    @Mapping(target = "time", expression = "java(ZonedDateTime.now())")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Like likeDtoToLike(LikeDto likeDto);
}
