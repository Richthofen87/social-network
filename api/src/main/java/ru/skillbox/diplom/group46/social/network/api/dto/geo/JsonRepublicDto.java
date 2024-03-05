package ru.skillbox.diplom.group46.social.network.api.dto.geo;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class JsonRepublicDto {
    private String name;
    private Set<JsonCityDto> areas = new HashSet<>();
}
