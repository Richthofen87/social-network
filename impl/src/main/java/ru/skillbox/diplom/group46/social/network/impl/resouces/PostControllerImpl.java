package ru.skillbox.diplom.group46.social.network.impl.resouces;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.diplom.group46.social.network.api.dto.post.CommentDto;
import ru.skillbox.diplom.group46.social.network.api.dto.post.CommentSearchDto;
import ru.skillbox.diplom.group46.social.network.api.dto.post.PostDto;
import ru.skillbox.diplom.group46.social.network.api.dto.post.PostSearchDto;

import ru.skillbox.diplom.group46.social.network.api.resource.post.PostController;
import ru.skillbox.diplom.group46.social.network.impl.service.CommentService;
import ru.skillbox.diplom.group46.social.network.impl.service.PostService;

import java.util.Set;


@Slf4j
@Component
@RestController
@RequiredArgsConstructor
public class PostControllerImpl implements PostController {
    private final PostService postService;
    private final CommentService commentService;


    @Override
    public ResponseEntity get(PostSearchDto postSearchDto, Pageable pageable) {
        log.info("PostControllerImpl.get() StartMethod");
        return ResponseEntity.ok(postService.get(postSearchDto, pageable));
    }

    @Override
    public ResponseEntity update(PostDto postDto) {
        log.info("PostControllerImpl.update() StartMethod");
        return ResponseEntity.ok(postService.update(postDto));
    }

    @Override
    public ResponseEntity post(PostDto postDto) {
        log.info("PostControllerImpl.create() StartMethod");
        return ResponseEntity.ok(postService.create(postDto));

    }

    @Override
    public ResponseEntity<CommentDto> createCommentToPost(String id, CommentDto commentDto) {
        log.info("PostControllerImpl.createCommentToPost() StartMethod");
        return ResponseEntity.ok(commentService.create(id, commentDto));
    }

    @Override
    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto comment) {
        log.info("PostControllerImpl.updateComment() StartMethod");
        return ResponseEntity.ok(commentService.update(comment));
    }

    @Override
    public ResponseEntity<Set<CommentDto>> getCommentsByPostId(
            @RequestBody CommentSearchDto commentSearchDTO,
            Pageable pageable) {
        log.info("PostControllerImpl.getCommentsByPostId() StartMethod");
        return ResponseEntity.ok(commentService.get(commentSearchDTO, pageable));
    }

}
