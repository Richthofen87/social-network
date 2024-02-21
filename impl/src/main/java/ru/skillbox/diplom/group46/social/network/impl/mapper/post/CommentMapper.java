package ru.skillbox.diplom.group46.social.network.impl.mapper.post;

import org.mapstruct.*;
import ru.skillbox.diplom.group46.social.network.api.dto.post.CommentDto;
import ru.skillbox.diplom.group46.social.network.domain.post.Comment;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Mapper(imports = {LocalDateTime.class, UUID.class, Date.class, Instant.class, SimpleDateFormat.class})
public interface CommentMapper {

    @Mapping(target = "commentType", source = "commentDto.commentType")
    @Mapping(target = "time", expression = "java(ZonedDateTime.now())")
    @Mapping(target = "timeChanged", expression = "java(ZonedDateTime.now())")
    @Mapping(target = "authorId", source = "id")
    @Mapping(target = "parentId", source = "id")
    @Mapping(target = "commentText", source = "commentDto.commentText")
    @Mapping(target = "postId", source = "id")
    @Mapping(target = "isBlocked", defaultValue = "false")
    @Mapping(target = "likeAmount", defaultValue = "0")
    @Mapping(target = "myLike", defaultValue = "false")
    @Mapping(target = "commentsCount", defaultValue = "0")
    @Mapping(target = "imagePath", defaultValue =
            "https://res.cloudinary.com/net/image/upload/v1700829702/fizdftx3pyrpa36vnhpp.jpg")
    Comment createCommentDtoToEntity(UUID id, CommentDto commentDto);

    @InheritInverseConfiguration
    CommentDto entityToDto(Comment comment);

    @Mapping(target = "timeChanged", expression = "java(ZonedDateTime.now())")
    @Mapping(target = "commentText", source = "commentDTO.commentText")
    @Mapping(target = "myLike", source = "commentDTO.myLike")
    @Mapping(target = "imagePath", source = "commentDTO.imagePath")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Comment updateCommentDtoToEntity(CommentDto commentDTO, @MappingTarget Comment comment);

    Set<CommentDto> entityListToDtoList(Set<Comment> postList);


}