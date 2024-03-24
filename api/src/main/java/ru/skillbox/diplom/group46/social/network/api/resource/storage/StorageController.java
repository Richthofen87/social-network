package ru.skillbox.diplom.group46.social.network.api.resource.storage;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@RequestMapping("/api/v1/storage")
public interface StorageController {
    @PostMapping
    String uploadFile(@RequestBody MultipartFile file) throws IOException;
}
