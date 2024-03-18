package ru.skillbox.diplom.group46.social.network.impl.resource.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.diplom.group46.social.network.api.resource.storage.StorageController;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StorageControllerImpl implements StorageController {
    @Override
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        return null;
    }
}
