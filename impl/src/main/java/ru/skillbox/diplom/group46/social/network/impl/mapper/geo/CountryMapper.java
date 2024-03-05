package ru.skillbox.diplom.group46.social.network.impl.mapper.geo;

import org.mapstruct.Mapper;

import ru.skillbox.diplom.group46.social.network.api.dto.geo.CountryDto;
import ru.skillbox.diplom.group46.social.network.domain.geo.Country;


import java.util.List;

@Mapper(uses = {CityMapper.class})
public interface CountryMapper {
    CountryDto countryToDto(Country country);
    List<CountryDto> countryToDtoList(List<Country> countries);
}