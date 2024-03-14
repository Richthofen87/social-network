package ru.skillbox.diplom.group46.social.network.impl.mapper.geo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skillbox.diplom.group46.social.network.api.dto.geo.JsonCityDto;
import ru.skillbox.diplom.group46.social.network.domain.geo.City;

import java.util.Set;

@Mapper
public interface JsonCityMapper {
    @Mapping(target = "name", source = "title")
    JsonCityDto cityToDto(City city);

    @Mapping(target = "title", source = "name")
    City jsonCityDtoToCity(JsonCityDto jsonCityDto);
    Set<JsonCityDto> jsonCityDtoToEntity(Set<City> city);

    Set<City> toEntityToCityDto(Set<JsonCityDto> jsonCityDto);
}
