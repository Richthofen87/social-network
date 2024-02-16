package ru.skillbox.diplom.group46.social.network.impl.service;

import java.util.Set;
import java.util.UUID;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group46.social.network.api.dto.post.PostDto;
import ru.skillbox.diplom.group46.social.network.api.dto.post.PostSearchDto;
import ru.skillbox.diplom.group46.social.network.domain.post.Post;
import ru.skillbox.diplom.group46.social.network.domain.post.Post_;
import ru.skillbox.diplom.group46.social.network.impl.mapper.PostMapper;
import ru.skillbox.diplom.group46.social.network.impl.repository.post.PostRepository;
import ru.skillbox.diplom.group46.social.network.impl.utils.specification.SpecificationUtil;


@Slf4j
@Service
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

    @Transactional
    public Set<PostDto> get(PostSearchDto postSearchDto, Pageable pageable) {
        log.info("PostService.get() StartMethod");
        Specification specification = SpecificationUtil
                .ContainsValue(Post_.postText, postSearchDto.getText()).and
                        (SpecificationUtil.isBetween(Post_.publishDate, postSearchDto.getDateFrom(), postSearchDto.getDateTo()))
                .and(SpecificationUtil.inListJoin(postSearchDto.getTags(),"tags",  "name"));
        return postMapper.postEntitySetToPostDtoSet(postRepository.findAll(specification, pageable).toSet());
    }

    @Transactional
    public PostDto update(PostDto postDto) {
        log.info("PostService.update() StartMethod");
        return postMapper.postEntityToPostDto(postRepository.saveAndFlush(postMapper.update(postDto,
                postRepository.findById(postDto.getId()).get())));
    }
}
