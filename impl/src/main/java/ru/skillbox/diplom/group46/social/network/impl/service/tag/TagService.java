package ru.skillbox.diplom.group46.social.network.impl.service.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.diplom.group46.social.network.api.dto.tag.TagDto;
import ru.skillbox.diplom.group46.social.network.domain.tag.Tag;
import ru.skillbox.diplom.group46.social.network.impl.mapper.post.TagMapper;
import ru.skillbox.diplom.group46.social.network.impl.repository.post.TagRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Transactional
    public List<TagDto> getTags() {
        List<Tag> allTags = tagRepository.findTagsOrderedByPostCountDesc();
        return allTags.stream().map(tagMapper::tagToTagDto).toList();
    }
}
