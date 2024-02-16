package ru.skillbox.diplom.group46.social.network.impl.mapper.geo;

import org.mapstruct.Mapper;

import ru.skillbox.diplom.group46.social.network.api.dto.geo.CountryDto;
import ru.skillbox.diplom.group46.social.network.domain.geo.Country;


import java.util.List;

@Mapper
public interface CountryMapper {
    Country dtoToEntity(CountryDto countryDto);
    CountryDto entityToDto(Country country);
    List<CountryDto> entityListToDtoList(List <Country> countryList);
}
