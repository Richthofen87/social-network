package ru.skillbox.diplom.group46.social.network.impl.mapper.post;

import org.mapstruct.*;
import ru.skillbox.diplom.group46.social.network.api.dto.post.PostDto;
import ru.skillbox.diplom.group46.social.network.domain.post.Post;


import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

@Mapper(imports = {UUID.class, ZonedDateTime.class},
        uses = {LikeReactionMapper.class, TagMapper.class})
public interface PostMapper {
    @Mapping(target = "tags", source = "postDto.tags")
    @Mapping(target = "isDeleted", defaultValue = "false")
    @Mapping(target = "reactionType", source = "postDto.reactionType")
    @Mapping(target = "time", expression = "java(ZonedDateTime.now())")
    @Mapping(target = "timeChanged",expression = "java(ZonedDateTime.now())")
    @Mapping(target = "publishDate", expression = "java(ZonedDateTime.now())")
    @Mapping(target = "imagePath", defaultValue = "https://res.cloudinary.com/net/image/upload/v1700829702/fizdftx3pyrpa36vnhpp.jpg")
    @Mapping(target = "likeAmount", defaultValue = "1")
    @Mapping(target = "commentsCount", defaultValue = "2")
    Post postDtoToPostEntity (PostDto postDto);


    PostDto postEntityToPostDto(Post post);

    Set<PostDto> postEntitySetToPostDtoSet(Set<Post> postSet);

    @InheritInverseConfiguration

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Post update(PostDto postDto, @MappingTarget Post post);

}
