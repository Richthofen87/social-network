package ru.skillbox.diplom.group46.social.network.impl.resource.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.diplom.group46.social.network.api.resource.storage.StorageController;
import ru.skillbox.diplom.group46.social.network.impl.service.storage.StorageService;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StorageControllerImpl implements StorageController {
    private final StorageService fileUpload;
    @Override
    public String uploadFile(@RequestBody MultipartFile file) throws IOException {
        return fileUpload.uploadFile(file);
    }
}
