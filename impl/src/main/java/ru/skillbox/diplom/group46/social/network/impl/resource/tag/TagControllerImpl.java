package ru.skillbox.diplom.group46.social.network.impl.resource.tag;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.diplom.group46.social.network.api.dto.tag.TagDto;
import ru.skillbox.diplom.group46.social.network.api.resource.tag.TagController;
import ru.skillbox.diplom.group46.social.network.impl.service.tag.TagService;

import java.util.List;

@Slf4j
@Component
@RestController
@RequiredArgsConstructor
public class TagControllerImpl implements TagController {

    private final TagService tagService;

    @Override
    public ResponseEntity<List<TagDto>> getTags() {
        log.info("TagControllerImpl.getTags() StartMethod");
        return ResponseEntity.ok(tagService.getTags());
    }
}
