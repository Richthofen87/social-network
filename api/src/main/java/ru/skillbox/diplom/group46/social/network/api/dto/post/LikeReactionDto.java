package ru.skillbox.diplom.group46.social.network.api.dto.post;

import lombok.Data;

@Data
public class LikeReactionDto {
    private String reactionType;
    private Long count;

    public LikeReactionDto(String reactionType, Long count) {
        this.reactionType = reactionType;
        this.count = count;
    }
}
