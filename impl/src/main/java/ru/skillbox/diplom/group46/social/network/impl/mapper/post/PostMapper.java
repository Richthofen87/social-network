package ru.skillbox.diplom.group46.social.network.impl.mapper.post;

import org.mapstruct.*;
import org.springframework.data.domain.Page;
import ru.skillbox.diplom.group46.social.network.api.dto.post.PostDto;
import ru.skillbox.diplom.group46.social.network.api.dto.post.enums.CommentTypeDto;
import ru.skillbox.diplom.group46.social.network.domain.post.Post;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Mapper(imports = {UUID.class, ZonedDateTime.class, CommentTypeDto.class},
        uses = {TagMapper.class, Page.class})

public interface PostMapper {

    @Mapping(target = "tags", source = "postDto.tags")
    @Mapping(target = "isDeleted", defaultValue = "false")
    @Mapping(target = "isBlocked", defaultValue = "false")
    @Mapping(target = "time", expression = "java(ZonedDateTime.now())")
    @Mapping(target = "timeChanged",expression = "java(ZonedDateTime.now())")
    @Mapping(target = "publishDate", expression = "java(ZonedDateTime.now())")
    @Mapping(target = "imagePath", defaultValue = "https://res.cloudinary.com/net/image/upload/v1700829702/fizdftx3pyrpa36vnhpp.jpg")
    Post postDtoToPostEntity (PostDto postDto);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PostDto postEntityToPostDto(Post post);

    List<PostDto> postEntitySetToPostDtoSet(List<Post> postSet);

    @InheritInverseConfiguration
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Post update(PostDto postDto, @MappingTarget Post post);

}
