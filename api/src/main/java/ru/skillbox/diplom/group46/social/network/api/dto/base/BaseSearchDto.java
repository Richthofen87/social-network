package ru.skillbox.diplom.group46.social.network.api.dto.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

/**
 * BaseSearchDto
 *
 * @author vladimir.sazonov
 */

@Getter
@Setter
@NoArgsConstructor
public class BaseSearchDto {
    private String uuid;
    private List<UUID> uuids;
    private Boolean isDeleted;
}