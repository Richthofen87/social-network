package ru.skillbox.diplom.group46.social.network.api.resource.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.diplom.group46.social.network.api.dto.post.*;

import java.util.UUID;

@RequestMapping("/api/v1/post")
public interface PostController {

    @GetMapping
    ResponseEntity<Page<PostDto>> get(PostSearchDto postSearchDto, Pageable pageable);

    @PutMapping
    ResponseEntity<PostDto> update(@RequestBody PostDto postDto);

    @PostMapping
    ResponseEntity<PostDto> post(@RequestBody PostDto postDto);

    @PutMapping("{id}/comment")
    ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto);

    @PostMapping("{id}/comment")
    ResponseEntity<CommentDto> createComment(@PathVariable UUID id, @RequestBody CommentDto commentDto);

    @PutMapping("{id}/comment/{commentId}")
    ResponseEntity<CommentDto> createSubComment(@RequestBody CommentDto commentDto,
                                                @PathVariable UUID id,
                                                @PathVariable UUID commentId);

    @DeleteMapping("{id}/comment/{commentId}")
    ResponseEntity<String> deleteComment(@PathVariable UUID commentId);

    @PostMapping("{id}/like")
    ResponseEntity<LikeDto> likePost(@PathVariable UUID id, @RequestBody LikeDto likeDto);

    @DeleteMapping("{id}/like")
    ResponseEntity<LikeDto> unlikePost(@PathVariable UUID id);

    @PostMapping("{id}/comment/{commentId}/like")
    ResponseEntity<LikeDto> likeComment(@PathVariable UUID commentId);

    @DeleteMapping("{id}/comment/{commentId}/like")
    ResponseEntity<LikeDto> unlikeComment(@PathVariable UUID commentId);

    @GetMapping("{postId}/comment")
    ResponseEntity<Page<CommentDto>> getComments(CommentSearchDto commentSearchDTO,
                                                Pageable pageable);

    @GetMapping("{postId}/comment/{commentId}/subcomment")
    ResponseEntity<Page<CommentDto>> getSubComments(CommentSearchDto commentSearchDto,
                                                   Pageable pageable);

    @GetMapping("newsPosts")
    ResponseEntity<Page<PostDto>> getNewsPosts(PostSearchDto postSearchDto, Pageable pageable);

    @DeleteMapping("{id}")
    ResponseEntity<String> deletePost(@PathVariable UUID id);
}






