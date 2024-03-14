package ru.skillbox.diplom.group46.social.network.api.dto.geo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.skillbox.diplom.group46.social.network.api.dto.base.BaseDto;

import java.util.HashSet;
import java.util.Set;


@Data
public class CountryDto extends BaseDto {
    private String title;
    private Set<CityDto> cities = new HashSet<>();
}
