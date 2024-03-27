package ru.skillbox.diplom.group46.social.network.impl.resource.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.diplom.group46.social.network.api.dto.post.*;
import ru.skillbox.diplom.group46.social.network.api.resource.post.PostController;
import ru.skillbox.diplom.group46.social.network.impl.service.post.CommentService;
import ru.skillbox.diplom.group46.social.network.impl.service.post.PostService;

import java.util.UUID;


@Slf4j
@RestController
@RequiredArgsConstructor
public class PostControllerImpl implements PostController {

    private final PostService postService;
    private final CommentService commentService;

    @Override
    public ResponseEntity<Page<PostDto>> get(PostSearchDto postSearchDto, Pageable pageable) {
        log.info("PostControllerImpl.get() StartMethod");
        return ResponseEntity.ok(postService.getPostsUser(postSearchDto, pageable));
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
    public ResponseEntity<CommentDto> createComment(UUID id, CommentDto commentDto) {
        return ResponseEntity.ok(commentService.createComment(id, commentDto));
    }

    @Override
    public ResponseEntity<CommentDto> createSubComment(CommentDto commentDto, UUID id, UUID commentId) {
        return ResponseEntity.ok(commentService.createSubComment(commentDto, id, commentId));
    }

    @Override
    public ResponseEntity<String> deleteComment(UUID commentId) {
        return ResponseEntity.ok(commentService.deleteComment(commentId));
    }

    @Override
    public ResponseEntity<LikeDto> likePost(UUID id, LikeDto likeDto) {
        log.info("likePost.create() StartMethod");
        return ResponseEntity.ok(postService.addLikeToPost(id, likeDto));
    }

    @Override
    public ResponseEntity<LikeDto> unlikePost(UUID id) {
        return ResponseEntity.ok(postService.deleteLikeToPost(id));
    }

    @Override
    public ResponseEntity<LikeDto> likeComment(UUID commentId) {
        return ResponseEntity.ok(commentService.addLikeToComment(commentId));
    }

    @Override
    public ResponseEntity<LikeDto> unlikeComment(UUID commentId) {
        return ResponseEntity.ok(commentService.deleteLikeToComment(commentId));
    }

    @Override
    public ResponseEntity<Page<CommentDto>> getComments(CommentSearchDto commentSearchDTO, Pageable pageable) {
        return ResponseEntity.ok(commentService.getComments(commentSearchDTO, pageable));
    }

    @Override
    public ResponseEntity<Page<CommentDto>> getSubComments(CommentSearchDto commentSearchDTO, Pageable pageable) {
        return ResponseEntity.ok(commentService.getSubComments(commentSearchDTO, pageable));
    }

    @Override
    public ResponseEntity<Page<PostDto>> getNewsPosts(PostSearchDto postSearchDto, Pageable pageable){
        return ResponseEntity.ok(postService.getNewsPosts(postSearchDto, pageable));
    }

    @Override
    public ResponseEntity<String> deletePost(UUID id) {
        return ResponseEntity.ok(postService.deletePost(id));
    }

}
