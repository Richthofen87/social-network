package ru.skillbox.diplom.group46.social.network.api.dto.geo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.skillbox.diplom.group46.social.network.api.dto.base.BaseDto;


import java.util.UUID;

@Data
public class CityDto extends BaseDto {
    private String title;
    private UUID countryId;
}
