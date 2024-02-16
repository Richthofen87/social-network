package ru.skillbox.diplom.group46.social.network.impl.mapper.post;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skillbox.diplom.group46.social.network.api.dto.tag.TagDto;
import ru.skillbox.diplom.group46.social.network.domain.tag.Tag;
@Mapper
public interface TagMapper {
    @Mapping(target = "isDeleted", defaultValue = "false")
    Tag tagDtoToTag(TagDto tagDto);

    TagDto tagToTagDto(Tag tag);
}
