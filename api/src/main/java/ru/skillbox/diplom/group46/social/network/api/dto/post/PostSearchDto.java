package ru.skillbox.diplom.group46.social.network.api.dto.post;

import lombok.Data;
import ru.skillbox.diplom.group46.social.network.api.dto.base.BaseSearchDto;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class PostSearchDto extends BaseSearchDto {

    private UUID authorId;
    private UUID accountId;
    private String author;
    private String text;
    private Boolean withFriends;
    private ZonedDateTime dateFrom;
    private ZonedDateTime dateTo;
    private List<String> tags;
}