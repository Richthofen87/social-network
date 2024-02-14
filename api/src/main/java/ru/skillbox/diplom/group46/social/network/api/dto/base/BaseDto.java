package ru.skillbox.diplom.group46.social.network.api.dto.base;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class BaseDto {
    private String uuid;
    private Boolean isDeleted;
}