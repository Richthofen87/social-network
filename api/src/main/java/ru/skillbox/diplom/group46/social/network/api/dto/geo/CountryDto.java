package ru.skillbox.diplom.group46.social.network.api.dto.geo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.skillbox.diplom.group46.social.network.api.dto.base.BaseDto;


@Data
@EqualsAndHashCode(callSuper = true)
public class CountryDto extends BaseDto {
    private String title;
}
