package ru.skillbox.diplom.group46.social.network.impl.service;

import java.util.Set;
import java.util.UUID;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group46.social.network.api.dto.post.CommentDto;
import ru.skillbox.diplom.group46.social.network.domain.post.Comment;
import ru.skillbox.diplom.group46.social.network.api.dto.post.CommentSearchDto;
import ru.skillbox.diplom.group46.social.network.domain.post.Comment_;
import ru.skillbox.diplom.group46.social.network.domain.post.Post;
import ru.skillbox.diplom.group46.social.network.impl.mapper.CommentMapper;
import ru.skillbox.diplom.group46.social.network.impl.repository.post.CommentRepository;
import ru.skillbox.diplom.group46.social.network.impl.repository.post.PostRepository;
import ru.skillbox.diplom.group46.social.network.impl.utils.specification.SpecificationUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentDto create(String postId, CommentDto commentDTO) {
        Post post = postRepository.getById(UUID.fromString(postId));
        post.setCommentsCount(post.getCommentsCount() + 1);
        postRepository.save(post);

        Comment commentDtoToEntity = commentMapper.createCommentDtoToEntity(UUID.fromString(postId), commentDTO);
        Comment savedComment = commentRepository.save(commentDtoToEntity);

        return commentMapper.entityToDto(savedComment);
    }

    @Transactional
    public CommentDto update(CommentDto commentDTO) {
        return commentMapper.entityToDto(commentRepository.saveAndFlush(commentMapper
                .updateCommentDtoToEntity(commentDTO, commentRepository.getById(commentDTO.getId()))));
    }

    @Transactional
    public Set<CommentDto> get(CommentSearchDto commentSearchDTO, Pageable pageable) {
        Specification specification = SpecificationUtil
                .equalValue(Comment_.isDeleted, commentSearchDTO.getIsDeleted())
                .and(SpecificationUtil.equalValue(Comment_.commentType, commentSearchDTO.getCommentType()))
                .and(SpecificationUtil.equalValueUUID(Comment_.authorId, commentSearchDTO.getAuthorId()))
                .and(SpecificationUtil.equalValueUUID(Comment_.parentId, commentSearchDTO.getParentId()))
                .and(SpecificationUtil.equalValueUUID(Comment_.postId, commentSearchDTO.getPostId()))
                .and(SpecificationUtil.equalValueUUID(Comment_.id, commentSearchDTO.getCommentId()));
        return commentMapper.entityListToDtoList(commentRepository.findAll(specification, pageable).toSet());
    }
}
