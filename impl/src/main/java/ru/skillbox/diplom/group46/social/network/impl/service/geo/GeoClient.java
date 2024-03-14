package ru.skillbox.diplom.group46.social.network.impl.service.geo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@FeignClient(name = "geo-service",url = "https://api.hh.ru/")
public interface GeoClient {

    @GetMapping("areas")
    String getListCities();
}
