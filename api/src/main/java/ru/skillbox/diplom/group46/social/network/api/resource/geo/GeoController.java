package ru.skillbox.diplom.group46.social.network.api.resource.geo;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.diplom.group46.social.network.api.dto.geo.CityDto;
import ru.skillbox.diplom.group46.social.network.api.dto.geo.CountryDto;



import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/geo")
public interface GeoController  {

    @PutMapping("/load")
    ResponseEntity<CountryDto> load();

    @GetMapping("/country")
    ResponseEntity<List<CountryDto>> country();

    @GetMapping("/country/{countryId}/city")
    ResponseEntity <List<CityDto>> city(@PathVariable UUID countryId);
}
