package ru.skillbox.diplom.group46.social.network.impl.mapper;

import org.mapstruct.*;
import ru.skillbox.diplom.group46.social.network.api.dto.post.PostDto;
import ru.skillbox.diplom.group46.social.network.domain.post.Post;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Mapper(imports = {UUID.class, Date.class, Instant.class, SimpleDateFormat.class},
        uses = {LikeReactionMapper.class})
public interface PostMapper {
    @Mapping(target = "tags", source = "postDto.tags")
    @Mapping(target = "isDeleted", defaultValue = "false")
    @Mapping(target = "reactionType", source = "postDto.reactionType")
    @Mapping(target = "time", dateFormat = "dd/MM/yyyy", expression = "java(Date.from(Instant.now()))")
    @Mapping(target = "timeChanged", dateFormat = "dd/MM/yyyy", expression = "java(Date.from(Instant.now()))")
    @Mapping(target = "publishDate", dateFormat = "dd/MM/yyyy", expression = "java(Date.from(Instant.now()))")
    @Mapping(target = "imagePath", defaultValue = "https://res.cloudinary.com/net/image/upload/v1700829702/fizdftx3pyrpa36vnhpp.jpg")
    @Mapping(target = "likeAmount", defaultValue = "1")
    @Mapping(target = "commentsCount", defaultValue = "2")
    Post postDtoToPostEntity (PostDto postDto);


    PostDto postEntityToPostDto(Post post);

    Set<PostDto> postEntitySetToPostDtoSet(Set<Post> postSet);

    @InheritInverseConfiguration
    @Mapping(target = "timeChanged", dateFormat = "dd/MM/yyyy", expression = "java(Date.from(Instant.now()))")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Post update(PostDto postDto, @MappingTarget Post post);

}
