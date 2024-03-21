package ru.skillbox.diplom.group46.social.network.api.dto.notifications;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

/**
 * CountDto
 *
 * @author vladimir.sazonov
 */

@Data
@AllArgsConstructor
public class CountDto {
    private ZonedDateTime timestamp;
    private PartCountDto data;
}