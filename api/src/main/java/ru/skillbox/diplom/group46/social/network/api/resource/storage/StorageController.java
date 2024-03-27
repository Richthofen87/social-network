package ru.skillbox.diplom.group46.social.network.api.resource.storage;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.diplom.group46.social.network.api.dto.storage.StorageDto;

import java.io.IOException;
@RequestMapping("/api/v1/storage")
public interface StorageController {
    @PostMapping
    StorageDto uploadFile(@RequestBody MultipartFile file) throws IOException;
}
