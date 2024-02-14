package ru.skillbox.diplom.group46.social.network.api.resource.base;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import ru.skillbox.diplom.group46.social.network.api.dto.base.BaseDto;
import ru.skillbox.diplom.group46.social.network.api.dto.base.BaseSearchDto;

import java.util.UUID;

public  interface BaseController<T extends BaseDto, S extends BaseSearchDto> {

    ResponseEntity<T> getById(UUID id);

    ResponseEntity<Page<T>> getAll(S searchDto);

    void create(T baseDto);

    T deleteById(UUID id);

    void update(T baseDto);
}