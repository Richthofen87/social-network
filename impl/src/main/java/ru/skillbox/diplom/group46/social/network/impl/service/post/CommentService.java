package ru.skillbox.diplom.group46.social.network.impl.service.post;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.diplom.group46.social.network.api.dto.post.CommentDto;
import ru.skillbox.diplom.group46.social.network.domain.post.Comment;
import ru.skillbox.diplom.group46.social.network.api.dto.post.CommentSearchDto;
import ru.skillbox.diplom.group46.social.network.domain.post.Comment_;
import ru.skillbox.diplom.group46.social.network.domain.post.Post;
import ru.skillbox.diplom.group46.social.network.impl.mapper.post.CommentMapper;
import ru.skillbox.diplom.group46.social.network.impl.repository.post.CommentRepository;
import ru.skillbox.diplom.group46.social.network.impl.repository.post.PostRepository;
import ru.skillbox.diplom.group46.social.network.impl.utils.specification.SpecificationUtil;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;


    public CommentDto createComment(String postId, CommentDto commentDTO) {
        Post post = postRepository.findById(UUID.fromString(postId)).get();
        AtomicInteger count = new AtomicInteger(post.getCommentsCount());
        post.setCommentsCount(count.incrementAndGet());
        postRepository.save(post);

        Comment commentDtoToEntity = commentMapper.createCommentDtoToEntity(UUID.fromString(postId), commentDTO);
        Comment savedComment = commentRepository.save(commentDtoToEntity);

        return commentMapper.entityToDto(savedComment);
    }


    public CommentDto updateComment(CommentDto commentDTO) {
        return commentMapper.entityToDto(commentRepository.saveAndFlush(commentMapper
                .updateCommentDtoToEntity(commentDTO, commentRepository.getById(commentDTO.getId()))));
    }


    @Transactional(readOnly = true)
    public Set<CommentDto> getComments(CommentSearchDto commentSearchDTO, Pageable pageable) {
        Specification specification = createSpecification(commentSearchDTO);
        return commentMapper.entityListToDtoList(commentRepository.findAll(specification, pageable).toSet());
    }

    @Transactional(readOnly = true)
    public Set<CommentDto> getSubComments(CommentSearchDto commentSearchDTO, Pageable pageable) {
        Specification specification = createSpecification(commentSearchDTO);
        return commentMapper.entityListToDtoList(commentRepository.findAll(specification, pageable).toSet());
    }


    public CommentDto createSubComment(CommentDto commentDto, String commentId) {
        Comment commentDtoToEntity = commentMapper.createCommentDtoToEntity(UUID.fromString(commentId), commentDto);
        Comment savedComment = commentRepository.save(commentDtoToEntity);

        return commentMapper.entityToDto(savedComment);
    }

    private Specification createSpecification(CommentSearchDto commentSearchDTO) {
        return SpecificationUtil
                .equalValue(Comment_.isDeleted, commentSearchDTO.getIsDeleted())
                .and(SpecificationUtil.equalValue(Comment_.commentType, commentSearchDTO.getCommentType()))
                .and(SpecificationUtil.equalValueUUID(Comment_.authorId, commentSearchDTO.getAuthorId()))
                .and(SpecificationUtil.equalValueUUID(Comment_.parentId, commentSearchDTO.getParentId()))
                .and(SpecificationUtil.equalValueUUID(Comment_.postId, commentSearchDTO.getPostId()))
                .and(SpecificationUtil.equalValueUUID(Comment_.id, commentSearchDTO.getCommentId()));
    }
}
