package ru.skillbox.diplom.group46.social.network.api.dto.post;

import lombok.Data;

@Data
public class LikeReactionDto {
    private String reactionType;
    private Integer count;
}
