package ru.skillbox.diplom.group46.social.network.impl.service.geo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import ru.skillbox.diplom.group46.social.network.api.dto.geo.CityDto;
import ru.skillbox.diplom.group46.social.network.api.dto.geo.CountryDto;
import ru.skillbox.diplom.group46.social.network.impl.mapper.geo.CityMapper;


import ru.skillbox.diplom.group46.social.network.impl.mapper.geo.CountryMapper;
import ru.skillbox.diplom.group46.social.network.impl.repository.geo.CityRepository;
import ru.skillbox.diplom.group46.social.network.impl.repository.geo.CountryRepository;


import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeoService {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;
    private final CityMapper cityMapper;
    private final CityRepository cityRepository;
    private static final String HH_API_BASE_URL = "https://api.hh.ru";
    private RestTemplate restTemplate;


    public CountryDto load() {
        String countriesUrl = HH_API_BASE_URL + "/areas/countries";
        String citiesUrl = HH_API_BASE_URL + "/areas";
        return null;
    }
    @Transactional(readOnly = true)
    public List<CountryDto> getCountry() {
        return countryMapper.entityListToDtoList(countryRepository.findAll());
    }

    @Transactional(readOnly = true)
    public List<CityDto> getCity(UUID countryId) {

        return cityMapper.entityListToDtoList(cityRepository.findAllById(countryId));
    }
}
