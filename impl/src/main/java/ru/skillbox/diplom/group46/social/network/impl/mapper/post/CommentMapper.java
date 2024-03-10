package ru.skillbox.diplom.group46.social.network.impl.mapper.post;

import org.mapstruct.*;
import ru.skillbox.diplom.group46.social.network.api.dto.post.CommentDto;
import ru.skillbox.diplom.group46.social.network.api.dto.post.enums.CommentTypeDto;
import ru.skillbox.diplom.group46.social.network.domain.post.Comment;

import java.time.ZonedDateTime;
import java.util.UUID;

@Mapper(imports = {UUID.class, ZonedDateTime.class, CommentTypeDto.class})
public interface CommentMapper {

    @Mapping(target = "isDeleted", defaultValue = "false")
    @Mapping(target = "commentType", source = "commentDto.commentType")
    @Mapping(target = "time", expression = "java(ZonedDateTime.now())")
    @Mapping(target = "timeChanged", expression = "java(ZonedDateTime.now())")
    @Mapping(target = "authorId", source = "commentDto.authorId")
    @Mapping(target = "parentId", source = "commentDto.parentId")
    @Mapping(target = "post.id", source = "commentDto.postId")
    @Mapping(target = "commentText", source = "commentDto.commentText")
    @Mapping(target = "isBlocked", defaultValue = "false")
    @Mapping(target = "myLike", defaultValue = "false")
    @Mapping(target = "imagePath", defaultValue =
            "https://res.cloudinary.com/net/image/upload/v1700829702/fizdftx3pyrpa36vnhpp.jpg")
    Comment createCommentDtoToEntity(CommentDto commentDto);

    @InheritInverseConfiguration(name = "createCommentDtoToEntity")
    CommentDto commentToCommentDto(Comment comment);

    @InheritInverseConfiguration(name = "updateCommentDtoToEntity")
    CommentDto updateCommentToCommentDto(Comment comment);

    @Mapping(target = "timeChanged", expression = "java(ZonedDateTime.now())")
    @Mapping(target = "commentText", source = "commentDTO.commentText")
    @Mapping(target = "myLike", source = "commentDTO.myLike")
    @Mapping(target = "imagePath", source = "commentDTO.imagePath")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Comment updateCommentDtoToEntity(CommentDto commentDTO, @MappingTarget Comment comment);

}