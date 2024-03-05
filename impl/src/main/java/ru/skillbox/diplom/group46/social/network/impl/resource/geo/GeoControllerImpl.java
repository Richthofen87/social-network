package ru.skillbox.diplom.group46.social.network.impl.resource.geo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import ru.skillbox.diplom.group46.social.network.api.dto.geo.CityDto;
import ru.skillbox.diplom.group46.social.network.api.dto.geo.CountryDto;
import ru.skillbox.diplom.group46.social.network.api.resource.geo.GeoController;
import ru.skillbox.diplom.group46.social.network.impl.service.geo.GeoService;


import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Component
@RestController
@RequiredArgsConstructor
public  class GeoControllerImpl implements GeoController {

    private final GeoService geoService;
    @Override
    public ResponseEntity<CountryDto> load() {
        log.info("GeoControllerImpl.load - start method");
        return ResponseEntity.ok((CountryDto) geoService.load());
    }

    @Override
    public ResponseEntity<List<CountryDto>> country() {
        log.info("GeoControllerImpl.country - start method");
        return ResponseEntity.ok(geoService.getCountry());
    }

    @Override
    public ResponseEntity<Set<CityDto>> city(UUID countryId) {
        log.info("GeoControllerImpl.city - start method");
        return ResponseEntity.ok(geoService.getCity(countryId));
    }
}
