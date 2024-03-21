package ru.skillbox.diplom.group46.social.network.impl.service.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.diplom.group46.social.network.api.dto.post.LikeDto;
import ru.skillbox.diplom.group46.social.network.api.dto.post.PostDto;
import ru.skillbox.diplom.group46.social.network.api.dto.post.PostSearchDto;
import ru.skillbox.diplom.group46.social.network.api.dto.post.enums.CommentTypeDto;
import ru.skillbox.diplom.group46.social.network.api.dto.post.enums.TypeDto;
import ru.skillbox.diplom.group46.social.network.domain.notifications.NotificationType;
import ru.skillbox.diplom.group46.social.network.domain.post.Comment;
import ru.skillbox.diplom.group46.social.network.domain.post.Like;
import ru.skillbox.diplom.group46.social.network.domain.post.Post;
import ru.skillbox.diplom.group46.social.network.domain.post.Post_;
import ru.skillbox.diplom.group46.social.network.domain.tag.Tag;
import ru.skillbox.diplom.group46.social.network.impl.mapper.post.LikeMapper;
import ru.skillbox.diplom.group46.social.network.impl.mapper.post.PostMapper;
import ru.skillbox.diplom.group46.social.network.impl.mapper.post.TagMapper;
import ru.skillbox.diplom.group46.social.network.impl.repository.post.CommentRepository;
import ru.skillbox.diplom.group46.social.network.impl.repository.post.LikeRepository;
import ru.skillbox.diplom.group46.social.network.impl.repository.post.PostRepository;
import ru.skillbox.diplom.group46.social.network.impl.repository.post.TagRepository;
import ru.skillbox.diplom.group46.social.network.impl.service.KafkaProducerService;
import ru.skillbox.diplom.group46.social.network.impl.utils.auth.CurrentUserExtractor;
import ru.skillbox.diplom.group46.social.network.impl.utils.specification.SpecificationUtil;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final KafkaProducerService kafkaProducerService;
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final PostMapper postMapper;
    private final LikeMapper likeMapper;
    private final TagMapper tagMapper;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;


    public PostDto create(PostDto postDto) {
        log.info("PostService.create() StartMethod");
        postDto.setAuthorId(CurrentUserExtractor.getCurrentUser().getId());
        postDto.setType(TypeDto.POSTED);

        Post post = postMapper.postDtoToPostEntity(postDto);

        Set<Tag> tags = postDto.getTags().stream()
                .map(tagDto -> {
                    Tag existingTag = tagRepository.findByName(tagDto.getName());
                    return existingTag != null ? existingTag : tagMapper.tagDtoToTag(tagDto);
                })
                .collect(Collectors.toSet());
        post.setTags(tags);
        kafkaProducerService.sendNotification(post.getAuthorId(), null,
                "Создание поста", NotificationType.POST);
        return postMapper.postEntityToPostDto(postRepository.saveAndFlush(post));
    }


    @Transactional(readOnly = true)
    public Page<PostDto> get(PostSearchDto postSearchDto, Pageable pageable) {
        log.info("PostService.get() StartMethod");
        UUID authorId = CurrentUserExtractor.getCurrentUser().getId();

        Specification<Post> specification = Specification
                .where(SpecificationUtil.equalValueUUID(Post_.authorId, authorId)
                        .and(SpecificationUtil.equalValue(Post_.isDeleted, postSearchDto.getIsDeleted())));

        Page<Post> page = postRepository.findAll(specification, pageable);

        List<PostDto> postDtos = page.getContent().stream()
                .map(postMapper::postEntityToPostDto)
                .collect(Collectors.toList());

        postDtos.forEach(postDto -> {
            postDto.setCommentsCount(commentRepository.commentCount(postDto.getId()));
            postDto.setLikeAmount(likeRepository.likeCount(postDto.getId()));
            postDto.setReactionType(likeRepository.getReactionType(postDto.getId()));
        });

        return new PageImpl<>(postDtos, pageable, page.getTotalElements());
    }


    public PostDto update(PostDto postDto) {
        log.info("PostService.update() StartMethod");
        postDto = postMapper.postEntityToPostDto(postRepository.saveAndFlush(postMapper.update(postDto,
                postRepository.getById(postDto.getId()))));
        postDto.setCommentsCount(commentRepository.commentCount(postDto.getId()));
        return postDto;
    }

    @Transactional(readOnly = true)
    public PostDto getPost(String id) {
        log.info("PostService.getPost() StartMethod");
        PostDto postDto = postMapper.postEntityToPostDto(postRepository.getById(UUID.fromString(id)));
        postDto.setCommentsCount(commentRepository.commentCount(UUID.fromString(id)));
        postDto.setLikeAmount(likeRepository.likeCount(UUID.fromString(id)));
        return postDto;
    }

    public LikeDto addLikeToPost(UUID postId, LikeDto likeDto) {
        likeDto.setAuthorId(CurrentUserExtractor.getCurrentUser().getId());
        likeDto.setItemId(postId);
        likeDto.setType(CommentTypeDto.POST);

        Like existingLike = likeRepository.findByPostIdAndAuthorIdAndReactionType(postId,
                likeDto.getAuthorId(), likeDto.getReactionType());
        if (existingLike != null && existingLike.getReactionType().equals(likeDto.getReactionType())) {
            existingLike.setIsDeleted(false);
            return likeMapper.likeToLikeDto(existingLike);
        }

        Like like = likeMapper.likeDtoToLike(likeDto);
        like.setPost(postRepository.getById(postId));
        like.setReactionType(likeDto.getReactionType());
        kafkaProducerService.sendNotification(like.getAuthorId(), null,
                "Установка лайка на пост", NotificationType.LIKE);
        return likeMapper.likeToLikeDto(likeRepository.save(like));
    }

    public LikeDto deleteLikeToPost(UUID postId) {
        UUID authorId = CurrentUserExtractor.getCurrentUser().getId();
        Like like = likeRepository.findByPostIdAndAuthorIdAndIsDeletedFalse(postId, authorId);
        like.setIsDeleted(true);
        return likeMapper.likeToLikeDto(likeRepository.save(like));
    }

    public String deletePost(UUID id) {
        List<Comment> comments = commentRepository.findByPostId(id);

        likeRepository.deleteByPostId(id);

        for (Comment comment : comments) {
            likeRepository.deleteByCommentId(comment.getId());
        }

        commentRepository.deleteByPostId(id);
        postRepository.deleteById(id);
        return "Post deleted";
    }

    public String deleteComment(UUID commentId) {
        likeRepository.deleteByCommentId(commentId);
        commentRepository.deleteById(commentId);
        return "Comment deleted";
    }

//    public void updateDelayedPost(PostDto post) {
//        return null;
//    }
}
