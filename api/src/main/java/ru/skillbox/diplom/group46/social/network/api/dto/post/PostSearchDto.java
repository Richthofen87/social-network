package ru.skillbox.diplom.group46.social.network.api.dto.post;

import lombok.Data;
import ru.skillbox.diplom.group46.social.network.api.dto.base.BaseDto;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostSearchDto extends BaseDto {

    private String author;
    private String text;
    private boolean withFriends;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private List<String> tags;

}