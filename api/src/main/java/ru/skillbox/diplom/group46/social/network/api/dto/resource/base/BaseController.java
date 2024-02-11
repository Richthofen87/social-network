package ru.skillbox.diplom.group46.social.network.api.dto.resource.base;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import ru.skillbox.diplom.group46.social.network.api.dto.dto.base.BaseDto;

import java.util.UUID;


public  interface  BaseController <Dto extends BaseDto> {

    ResponseEntity<Dto> getById(UUID id);

    ResponseEntity<Page<Dto>> getAll(BaseSearchDto baseSearchDto);

    void create(Dto baseDto);

    Dto deleteById(UUID id);

    void update(Dto baseDto);
}