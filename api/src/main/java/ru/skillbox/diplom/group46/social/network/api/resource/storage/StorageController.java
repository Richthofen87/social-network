package ru.skillbox.diplom.group46.social.network.api.resource.storage;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/api/v1")
public interface StorageController {
    @PostMapping("/storage")
    String uploadFile(@RequestParam("image") MultipartFile multipartFile) throws IOException;

}
