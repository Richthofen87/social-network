package ru.skillbox.diplom.group46.social.network.api.resource.post;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.diplom.group46.social.network.api.dto.post.CommentDto;
import ru.skillbox.diplom.group46.social.network.api.dto.post.CommentSearchDto;
import ru.skillbox.diplom.group46.social.network.api.dto.post.PostDto;
import ru.skillbox.diplom.group46.social.network.api.dto.post.PostSearchDto;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/post")
public interface PostController {

    @GetMapping("/")
    ResponseEntity get(@RequestBody PostSearchDto postSearchDto, Pageable pageable);

    @PutMapping("/")
    ResponseEntity update(@RequestBody PostDto postDto);

    @PostMapping("/")
    ResponseEntity post(@RequestBody PostDto postDto);

    @PostMapping("/{id}/comment")
    ResponseEntity<CommentDto> createCommentToPost(@PathVariable String id, @RequestBody CommentDto commentDto);

    @PutMapping("/{id}/comment")
    ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto);

    @GetMapping("/{postId}/comment")
    ResponseEntity<Set<CommentDto>> getCommentsByPostId(@RequestBody CommentSearchDto commentSearchDTO,
                                                        Pageable pageable);
}






