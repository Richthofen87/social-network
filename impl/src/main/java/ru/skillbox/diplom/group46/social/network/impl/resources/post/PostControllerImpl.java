package ru.skillbox.diplom.group46.social.network.impl.resources.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.diplom.group46.social.network.api.dto.post.*;
import ru.skillbox.diplom.group46.social.network.api.resource.post.PostController;
import ru.skillbox.diplom.group46.social.network.impl.service.post.CommentService;
import ru.skillbox.diplom.group46.social.network.impl.service.post.PostService;
import java.util.Set;


@Slf4j
@Component
@RestController
@RequiredArgsConstructor
public class PostControllerImpl implements PostController {

    private final PostService postService;
    private final CommentService commentService;

    @Override
    public ResponseEntity<Set<PostDto>> get(PostSearchDto postSearchDto, Pageable pageable) {
        log.info("PostControllerImpl.get() StartMethod");
        return ResponseEntity.ok(postService.get(postSearchDto, pageable));
    }

    @Override
    public ResponseEntity<PostDto> update(PostDto postDto) {
        log.info("PostControllerImpl.update() StartMethod");
        return ResponseEntity.ok(postService.update(postDto));
    }

    @Override
    public ResponseEntity<PostDto> post(PostDto postDto) {
        log.info("PostControllerImpl.create() StartMethod");
        return ResponseEntity.ok(postService.create(postDto));
    }

    @Override
    public ResponseEntity<CommentDto> updateComment(CommentDto comment) {
        return ResponseEntity.ok(commentService.updateComment(comment));
    }

    @Override
    public ResponseEntity<CommentDto> createComment(String id, CommentDto commentDto) {
        return ResponseEntity.ok(commentService.createComment(id, commentDto));
    }

    @Override
    public ResponseEntity<CommentDto> createSubComment(CommentDto commentDto, String commentId) {
        return ResponseEntity.ok(commentService.createSubComment(commentDto, commentId));
    }

    @Override
    public ResponseEntity<Void> deleteComment(String id, String commentId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> updateDelayedPost(PostDto post) {
        return null;
    }

    @Override
    public ResponseEntity<LikeDto> likePost(String id) {
        log.info("likePost.create() StartMethod");
        return null;
    }

    @Override
    public ResponseEntity<Void> unlikePost(String id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> likeComment(String id, String commentId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> unlikeComment(String id, String commentId) {
        return null;
    }

    @Override
    public ResponseEntity<Set<CommentDto>> getComments(CommentSearchDto commentSearchDTO, Pageable pageable) {
        return ResponseEntity.ok(commentService.getComments(commentSearchDTO, pageable));
    }

    @Override
    public ResponseEntity<Set<CommentDto>> getSubComments(CommentSearchDto commentSearchDTO, Pageable pageable) {
        return ResponseEntity.ok(commentService.getSubComments(commentSearchDTO, pageable));
    }

    @Override
    public ResponseEntity<PostDto> getPost(String id) {
        return ResponseEntity.ok(postService.getPost(id));
    }

    @Override
    public ResponseEntity<Void> deletePost(String id) {
        return null;
    }

}
