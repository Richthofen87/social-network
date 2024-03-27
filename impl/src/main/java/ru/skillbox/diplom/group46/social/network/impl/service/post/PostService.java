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
import ru.skillbox.diplom.group46.social.network.domain.notifications.NotificationType;
import ru.skillbox.diplom.group46.social.network.domain.post.Comment;
import ru.skillbox.diplom.group46.social.network.domain.post.Like;
import ru.skillbox.diplom.group46.social.network.domain.post.Post;
import ru.skillbox.diplom.group46.social.network.domain.post.Post_;
import ru.skillbox.diplom.group46.social.network.domain.tag.Tag;
import ru.skillbox.diplom.group46.social.network.domain.user.User;
import ru.skillbox.diplom.group46.social.network.impl.mapper.post.LikeMapper;
import ru.skillbox.diplom.group46.social.network.impl.mapper.post.PostMapper;
import ru.skillbox.diplom.group46.social.network.impl.mapper.post.TagMapper;
import ru.skillbox.diplom.group46.social.network.impl.repository.post.CommentRepository;
import ru.skillbox.diplom.group46.social.network.impl.repository.post.LikeRepository;
import ru.skillbox.diplom.group46.social.network.impl.repository.post.PostRepository;
import ru.skillbox.diplom.group46.social.network.impl.repository.post.TagRepository;
import ru.skillbox.diplom.group46.social.network.impl.repository.user.UserRepository;
import ru.skillbox.diplom.group46.social.network.impl.service.kafka.KafkaProducerService;
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
    private final UserRepository userRepository;
    private final LikeMapper likeMapper;
    private final TagMapper tagMapper;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;


    public PostDto create(PostDto postDto) {
        log.info("PostService.create() StartMethod");
        postDto.setAuthorId(CurrentUserExtractor.getCurrentUserFromAuthentication().getId());

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
    public Page<PostDto> getPostsUser(PostSearchDto postSearchDto, Pageable pageable) {
        log.info("PostService.get() StartMethod");

        Specification<Post> specification =
                Specification.where(SpecificationUtil.equalValue(Post_.isDeleted, false))
                        .and(SpecificationUtil.equalValueUUID(Post_.authorId, postSearchDto.getAccountId()));

        return getPostDto(pageable, specification);
    }

    @Transactional(readOnly = true)
    public Page<PostDto> getNewsPosts(PostSearchDto postSearchDto, Pageable pageable) {
        log.info("PostService.get() StartMethod");

        List<UUID> authorIds = userRepository.findIdByFirstName(postSearchDto.getAuthor())
                .stream()
                .map(User::getId)
                .toList();

        Specification<Post> specification =
                Specification.where(SpecificationUtil.equalValue(Post_.isDeleted, false))
                .and(SpecificationUtil.isContainsValue(Post_.postText, postSearchDto.getText()))
                .and(SpecificationUtil.isContainsAuthor(Post_.authorId, authorIds))
                .and(SpecificationUtil.withFriends(postSearchDto.getWithFriends(), CurrentUserExtractor.getCurrentUserFromAuthentication().getId()))
                .and(SpecificationUtil.isBetween(Post_.time, postSearchDto.getDateFrom(), postSearchDto.getDateTo())
                .and(SpecificationUtil.IsContainsTags(postSearchDto.getTags())));

        return getPostDto(pageable, specification);
    }

    private PageImpl<PostDto> getPostDto(Pageable pageable, Specification<Post> specification) {
        Page<Post> page = postRepository.findAll(specification, pageable);

        List<PostDto> postDtos = page.getContent().stream()
                .map(postMapper::postEntityToPostDto)
                .collect(Collectors.toList());

        postDtos.forEach(postDto -> {
            postDto.setCommentsCount(commentRepository.commentCount(postDto.getId()));
            postDto.setLikeAmount(likeRepository.likeCountForPost(postDto.getId()));
            postDto.setReactionType(likeRepository.getReactionType(postDto.getId()));
            String myReaction = likeRepository.getMyReactionForPost(
                    CurrentUserExtractor.getCurrentUserFromAuthentication().getId(),
                    postDto.getId());
            postDto.setMyReaction(myReaction);
            postDto.setMyLike(myReaction != null);
            String withFriends = likeRepository.isContainsFriends(
                    CurrentUserExtractor.getCurrentUserFromAuthentication().getId(),
                    postDto.getAuthorId());
            postDto.setWithFriends(withFriends != null && withFriends.equals("FRIEND"));
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

    public LikeDto addLikeToPost(UUID postId, LikeDto likeDto) {
        UUID authorId = CurrentUserExtractor.getCurrentUserFromAuthentication().getId();
        likeDto.setAuthorId(authorId);
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
        UUID authorId = CurrentUserExtractor.getCurrentUserFromAuthentication().getId();
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
}
