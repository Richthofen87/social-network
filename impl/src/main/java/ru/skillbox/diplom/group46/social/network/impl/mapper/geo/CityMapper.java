package ru.skillbox.diplom.group46.social.network.impl.mapper.geo;



import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import ru.skillbox.diplom.group46.social.network.api.dto.geo.CityDto;
import ru.skillbox.diplom.group46.social.network.domain.geo.City;

import java.util.Set;


@Mapper
public interface CityMapper {

    @Mapping(target = "countryId", source = "country.id")
    CityDto countryToDto(City city);

    Set<CityDto>  cityDtoToEntity(Set<City> city);

}
