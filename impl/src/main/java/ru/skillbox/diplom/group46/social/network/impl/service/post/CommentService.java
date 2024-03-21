package ru.skillbox.diplom.group46.social.network.impl.service.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.diplom.group46.social.network.api.dto.post.CommentDto;
import ru.skillbox.diplom.group46.social.network.api.dto.post.CommentSearchDto;
import ru.skillbox.diplom.group46.social.network.api.dto.post.LikeDto;
import ru.skillbox.diplom.group46.social.network.api.dto.post.enums.CommentTypeDto;
import ru.skillbox.diplom.group46.social.network.domain.notifications.NotificationType;
import ru.skillbox.diplom.group46.social.network.domain.post.Comment;
import ru.skillbox.diplom.group46.social.network.domain.post.Comment_;
import ru.skillbox.diplom.group46.social.network.domain.post.Like;
import ru.skillbox.diplom.group46.social.network.domain.post.enums.CommentType;
import ru.skillbox.diplom.group46.social.network.impl.mapper.post.CommentMapper;
import ru.skillbox.diplom.group46.social.network.impl.mapper.post.LikeMapper;
import ru.skillbox.diplom.group46.social.network.impl.repository.post.CommentRepository;
import ru.skillbox.diplom.group46.social.network.impl.repository.post.LikeRepository;
import ru.skillbox.diplom.group46.social.network.impl.repository.post.PostRepository;
import ru.skillbox.diplom.group46.social.network.impl.service.KafkaProducerService;
import ru.skillbox.diplom.group46.social.network.impl.utils.auth.CurrentUserExtractor;
import ru.skillbox.diplom.group46.social.network.impl.utils.specification.SpecificationUtil;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final KafkaProducerService kafkaProducerService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final LikeMapper likeMapper;


    public CommentDto createComment(UUID postId, CommentDto commentDto) {
        commentDto.setAuthorId(CurrentUserExtractor.getCurrentUser().getId());
        commentDto.setCommentType(CommentTypeDto.POST);
        commentDto.setParentId(postId);
        commentDto.setPostId(postId);

        Comment comment = commentMapper.createCommentDtoToEntity(commentDto);
        comment.setPost(postRepository.getById(postId));
        kafkaProducerService.sendNotification(comment.getAuthorId(), null,
                "Отправка коммента на пост", NotificationType.POST_COMMENT);
        return commentMapper.commentToCommentDto(commentRepository.save(comment));
    }

    public LikeDto addLikeToComment(UUID commentId) {
        UUID authorId = CurrentUserExtractor.getCurrentUser().getId();

        Like existingLike = likeRepository.findByCommentIdAndAuthorId(commentId, authorId);
        if (existingLike != null) {
            existingLike.setIsDeleted(false);
            return likeMapper.likeToLikeDto(likeRepository.save(existingLike));
        }

        Like like = new Like();
        like.setIsDeleted(false);
        like.setAuthorId(authorId);
        like.setItemId(commentId);
        like.setTime(ZonedDateTime.now());
        like.setType(CommentType.COMMENT);
        like.setComment(commentRepository.getById(commentId));

        kafkaProducerService.sendNotification(like.getAuthorId(), null,
                "Установка лайка на коммент", NotificationType.LIKE);

        return likeMapper.likeToLikeDto(likeRepository.save(like));
    }


    public LikeDto deleteLikeToComment(UUID commentId) {
        UUID authorId = CurrentUserExtractor.getCurrentUser().getId();
        Like like = likeRepository.findByCommentIdAndAuthorIdAndTypeAndIsDeletedFalse(commentId, authorId, CommentType.COMMENT);
        like.setIsDeleted(true);
        return likeMapper.likeToLikeDto(likeRepository.save(like));
    }


    public CommentDto updateComment(CommentDto commentDTO, UUID postId) {
        CommentDto commentDto = commentMapper.commentToCommentDto(commentRepository.saveAndFlush(commentMapper
                .updateCommentDtoToEntity(commentDTO, commentRepository.getById(postId))));
        commentDto.setCommentsCount(commentRepository.commentCount(postId));
        return commentDTO;
    }

    @Transactional(readOnly = true)
    public Page<CommentDto> getComments(CommentSearchDto commentSearchDTO, Pageable pageable) {
        Specification<Comment> specification = Specification
                .where(SpecificationUtil.equalValue(Comment_.parentId, commentSearchDTO.getPostId()))
                .and(SpecificationUtil.equalValue(Comment_.isDeleted, commentSearchDTO.getIsDeleted()));

        return getCommentDtoPage(commentSearchDTO, pageable, specification);
    }


    @Transactional(readOnly = true)
    public Page<CommentDto> getSubComments(CommentSearchDto commentSearchDTO, Pageable pageable) {
        Specification<Comment> specification = Specification
                .where(SpecificationUtil.equalValue(Comment_.parentId, commentSearchDTO.getCommentId())
                        .and(SpecificationUtil.equalValue(Comment_.isDeleted, commentSearchDTO.getIsDeleted())));

        return getCommentDtoPage(commentSearchDTO, pageable, specification);
    }


    private Page<CommentDto> getCommentDtoPage(CommentSearchDto commentSearchDTO, Pageable pageable, Specification<Comment> specification) {
        Page<Comment> comments = commentRepository.findAll(specification, pageable);

        List<CommentDto> commentDtos = comments.getContent().stream()
                .map(commentMapper::commentToCommentDto)
                .collect(Collectors.toList());

        commentDtos.forEach(commentDto -> {
            commentDto.setCommentsCount(commentRepository.commentCount(commentDto.getId()));
            commentDto.setLikeAmount(likeRepository.likeCount(commentDto.getId()));
        });

        return new PageImpl<>(commentDtos, pageable, comments.getTotalElements());
    }


    public CommentDto createSubComment(CommentDto commentDto, UUID postId, UUID commentId) {
        commentDto.setAuthorId(CurrentUserExtractor.getCurrentUser().getId());
        commentDto.setCommentType(CommentTypeDto.COMMENT);
        commentDto.setParentId(commentId);
        commentDto.setPostId(postId);

        Comment comment = commentMapper.createCommentDtoToEntity(commentDto);
        comment.setPost(postRepository.getById(postId));
        kafkaProducerService.sendNotification(comment.getAuthorId(), null,
                "Отправка коммента на коммент", NotificationType.COMMENT_COMMENT);
        return commentMapper.commentToCommentDto(commentRepository.save(comment));
    }
}