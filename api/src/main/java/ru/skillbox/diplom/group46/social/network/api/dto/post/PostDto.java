package ru.skillbox.diplom.group46.social.network.api.dto.post;

import lombok.Data;
import ru.skillbox.diplom.group46.social.network.api.dto.base.BaseDto;
import ru.skillbox.diplom.group46.social.network.api.dto.post.enums.TypeDto;
import ru.skillbox.diplom.group46.social.network.api.dto.tag.TagDto;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class PostDto extends BaseDto {

    private String time;
    private String timeChanged;
    private UUID authorId;
    private String title;
    private TypeDto type;
    private String postText;
    private Boolean isBlocked;
    private Integer commentsCount;
    private List<LikeReactionDto> reactionType;
    private String myReaction;
    private Integer likeAmount;
    private Boolean myLike;
    private String imagePath;
    private String publishDate;
    private Set<TagDto> tags;

}