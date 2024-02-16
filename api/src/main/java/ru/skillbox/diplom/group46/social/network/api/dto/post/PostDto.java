package ru.skillbox.diplom.group46.social.network.api.dto.post;

import lombok.Data;
import ru.skillbox.diplom.group46.social.network.api.dto.base.BaseDto;
import ru.skillbox.diplom.group46.social.network.api.dto.tag.TagDto;
import ru.skillbox.diplom.group46.social.network.api.dto.post.enums.Type;
import java.util.Set;

@Data
public class PostDto extends BaseDto {

    private String time;
    private String timeChanged;
    private String authorId;
    private String title;
    private Type type;
    private String postText;
    private boolean isBlocked;
    private Integer commentsCount;
    private Set<LikeReactionDto> reactionType;
    private String myReaction;
    private Integer likeAmount;
    private boolean myLike;
    private String imagePath;
    private String publishDate;
    private Set<TagDto> tags;

}