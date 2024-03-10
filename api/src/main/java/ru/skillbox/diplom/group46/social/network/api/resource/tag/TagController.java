package ru.skillbox.diplom.group46.social.network.api.resource.tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.diplom.group46.social.network.api.dto.tag.TagDto;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tag/")
public interface TagController {

    @GetMapping
    ResponseEntity<List<TagDto>> getTags();
}
