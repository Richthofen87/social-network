package ru.skillbox.diplom.group46.social.network.api.dto.geo;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;


@Data
public class JsonCountryDto {
    private String name;
    private Set<JsonRepublicDto> areas = new HashSet<>();
}
