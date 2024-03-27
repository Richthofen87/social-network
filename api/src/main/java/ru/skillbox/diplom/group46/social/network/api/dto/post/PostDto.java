package ru.skillbox.diplom.group46.social.network.api.dto.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.skillbox.diplom.group46.social.network.api.dto.base.BaseDto;
import ru.skillbox.diplom.group46.social.network.api.dto.post.enums.TypeDto;
import ru.skillbox.diplom.group46.social.network.api.dto.tag.TagDto;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class PostDto extends BaseDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "UTC")
    private ZonedDateTime time;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "UTC")
    private ZonedDateTime timeChanged;

    private UUID authorId;
    private String title;
    private TypeDto type;
    private String postText;
    private Boolean isBlocked;
    private Boolean withFriends;
    private Integer commentsCount;
    private List<LikeReactionDto> reactionType;
    private String myReaction;
    private Integer likeAmount;
    private Boolean myLike;
    private String imagePath;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "UTC")
    private ZonedDateTime publishDate;
    private Set<TagDto> tags;


}