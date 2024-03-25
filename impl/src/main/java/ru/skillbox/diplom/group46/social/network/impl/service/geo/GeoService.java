package ru.skillbox.diplom.group46.social.network.impl.service.geo;
import com.nimbusds.jose.shaded.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.diplom.group46.social.network.api.dto.geo.*;
import ru.skillbox.diplom.group46.social.network.domain.geo.City;
import ru.skillbox.diplom.group46.social.network.domain.geo.Country;
import ru.skillbox.diplom.group46.social.network.impl.mapper.geo.CityMapper;


import ru.skillbox.diplom.group46.social.network.impl.mapper.geo.CountryMapper;
import ru.skillbox.diplom.group46.social.network.impl.mapper.geo.JsonCityMapper;
import ru.skillbox.diplom.group46.social.network.impl.repository.geo.CityRepository;
import ru.skillbox.diplom.group46.social.network.impl.repository.geo.CountryRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class GeoService {

    private final CountryRepository countryRepository;
    private final CityMapper cityMapper;
    private final CityRepository cityRepository;
    private final GeoClient geoClient;
    private final CountryMapper countryMapper;
    private final JsonCityMapper jsonCityMapper;

    @SneakyThrows
    public List<CountryDto> getLoad() {
        File city = new File("city.json"); // Создается файл json
        new FileOutputStream(city, false).close(); // Стирание старых данных, для записи новых
        FileOutputStream cityStream = new FileOutputStream(city);
        byte[] cities = geoClient.getListCities().getBytes(); // получение массива байтов из сайта
        cityStream.write(cities); // запись массиива полученных байтов в выходной поток
        cityStream.close(); // закрываем выходной потое

        FileReader fileReader = new FileReader("city.json"); // чтение
        Gson gson = new Gson();
        JsonCountryDto[] jsonCountryDto = gson.fromJson(fileReader, JsonCountryDto[].class); // десериализация

        countryRepository.hardDeleteAll(); // очистка
        cityRepository.hardDeleteAll();

        for(JsonCountryDto dto : jsonCountryDto) {
            Country country = new Country();
            country.setTitle(dto.getName());
            Set<JsonRepublicDto> republics = dto.getAreas();
            Set<JsonCityDto> jsonCityDto = new HashSet<>();
            republics.forEach(jsonRepublicDto -> jsonCityDto.addAll(jsonRepublicDto.getAreas()));
            Set<City> citiesSet = jsonCityMapper.toEntityToCityDto(jsonCityDto);
            citiesSet.forEach(cityEntity -> cityEntity.setCountry(country));
            countryRepository.save(country);
            if (citiesSet.size() == 0) {
                republics.stream().forEach(rep -> {
                    JsonCityDto cityName = new JsonCityDto();
                    cityName.setName(rep.getName());
                    jsonCityDto.add(cityName);
                });
            }

            citiesSet.stream().findAny().ifPresent(city1 -> {
                republics.stream().forEach(rep -> {
                    JsonCityDto cityName = new JsonCityDto();
                    cityName.setName(rep.getName());
                    jsonCityDto.add(cityName);
                });
            });

            citiesSet = jsonCityMapper.toEntityToCityDto(jsonCityDto);
            citiesSet.forEach(cityEntity -> cityEntity.setCountry(country));
            cityRepository.saveAll(citiesSet);
        }
        List<Country> countries  = countryRepository.findAll();
        return countryMapper.countryToDtoList(countries);
    }

    public List<CountryDto> getCountry() {
         log.info("GeoService.getCountry - start method");
        if (countryRepository.findAll().isEmpty()) {
            getLoad();
        }
        return countryMapper.countryToDtoList(countryRepository.findAll());
    }
    @Transactional(readOnly = true)
    public Set<CityDto> getCity(UUID countryId) {
        log.info("GeoService.getCity - start methode");
        return cityMapper.cityDtoToEntity(cityRepository.findByCountryId(countryId));
    }
}
