package ru.skillbox.diplom.group46.social.network.api.resource.post;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.diplom.group46.social.network.api.dto.post.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/post")
public interface PostController {

    @GetMapping
    ResponseEntity<Set<PostDto>> get(PostSearchDto postSearchDto, Pageable pageable);

    @PutMapping
    ResponseEntity<PostDto> update(@RequestBody PostDto postDto);

    @PostMapping
    ResponseEntity<PostDto> post(@RequestBody PostDto postDto);

    @PutMapping("{id}/comment")
    ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto);

    @PostMapping("{id}/comment")
    ResponseEntity<CommentDto> createComment(@PathVariable String id, @RequestBody CommentDto commentDto);

    @PutMapping("{id}/comment/{commentId}")
    ResponseEntity<CommentDto> createSubComment(@RequestBody CommentDto commentDto, @PathVariable String commentId);

    @DeleteMapping("{id}/comment/{commentId}")
    ResponseEntity<Void> deleteComment(@PathVariable String id, @PathVariable String commentId);

    @PutMapping("delayed")
    ResponseEntity<Void> updateDelayedPost(@RequestBody PostDto post);

    @PostMapping("{id}/like")
    ResponseEntity<LikeDto> likePost(@PathVariable String id);

    @DeleteMapping("{id}/like")
    ResponseEntity<Void> unlikePost(@PathVariable String id);

    @PostMapping("{id}/comment/{commentId}/like")
    ResponseEntity<Void> likeComment(@PathVariable String id, @PathVariable String commentId);

    @DeleteMapping("{id}/comment/{commentId}/like")
    ResponseEntity<Void> unlikeComment(@PathVariable String id, @PathVariable String commentId);

    @GetMapping("{postId}/comment")
    ResponseEntity<Set<CommentDto>> getComments(@RequestBody CommentSearchDto commentSearchDTO,
                                                        Pageable pageable);

    @GetMapping("{postId}/comment/{commentId}/subcomment")
    ResponseEntity<Set<CommentDto>> getSubComments(@RequestBody CommentSearchDto commentSearchDto,
                                                   Pageable pageable);

    @GetMapping("{id}")
    ResponseEntity<PostDto> getPost(@PathVariable String id);

    @DeleteMapping("{id}")
    ResponseEntity<Void> deletePost(@PathVariable String id);
}






