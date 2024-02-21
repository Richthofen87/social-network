package ru.skillbox.diplom.group46.social.network.impl.mapper.geo;



import org.mapstruct.Mapper;

import ru.skillbox.diplom.group46.social.network.api.dto.geo.CityDto;
import ru.skillbox.diplom.group46.social.network.domain.geo.City;



import java.util.List;


@Mapper
public interface CityMapper {
    City dtoToEntity(CityDto cityDto);

    CityDto entityToDto(City city);

    List<CityDto> entityListToDtoList(List<City> cityList);

}
