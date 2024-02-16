package ru.skillbox.diplom.group46.social.network.impl.service.post;

import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.diplom.group46.social.network.api.dto.post.PostDto;
import ru.skillbox.diplom.group46.social.network.api.dto.post.PostSearchDto;
import ru.skillbox.diplom.group46.social.network.domain.post.Post;
import ru.skillbox.diplom.group46.social.network.domain.post.Post_;
import ru.skillbox.diplom.group46.social.network.impl.mapper.post.PostMapper;
import ru.skillbox.diplom.group46.social.network.impl.repository.post.PostRepository;
import ru.skillbox.diplom.group46.social.network.impl.utils.specification.SpecificationUtil;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;


    public PostDto create(PostDto postDto) {
        log.info("PostService.create() StartMethod");
        Post post = postMapper.postDtoToPostEntity(postDto);
        post.getReactionType().forEach(likeReaction -> likeReaction.setPost(post));
        return postMapper.postEntityToPostDto(postRepository.saveAndFlush(post));
    }

    @Transactional(readOnly = true)
    public Set<PostDto> get(PostSearchDto postSearchDto, Pageable pageable) {
        log.info("PostService.get() StartMethod");
        Specification specification = SpecificationUtil
                .ContainsValue(Post_.postText, postSearchDto.getText()).and
                        (SpecificationUtil.isBetween(Post_.publishDate, postSearchDto.getDateFrom(), postSearchDto.getDateTo()))
                .and(SpecificationUtil.inListJoin(postSearchDto.getTags(), "tags", "name"));
        return postMapper.postEntitySetToPostDtoSet(postRepository.findAll(specification, pageable).toSet());
    }


    public PostDto update(PostDto postDto) {
        log.info("PostService.update() StartMethod");
        return postMapper.postEntityToPostDto(postRepository.saveAndFlush(postMapper.update(postDto,
                postRepository.findById(postDto.getId()).get())));
    }

    @Transactional(readOnly = true)
    public PostDto getPost(String id) {
        log.info("PostService.getPost() StartMethod");
        return postMapper.postEntityToPostDto(postRepository.findById(UUID.fromString(id)).get());
    }
}
