package ru.skillbox.diplom.group46.social.network.impl.service.storage;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
@Log4j2
@Service
@RequiredArgsConstructor
public class StorageService {
    private final Cloudinary cloudinary;
    public String uploadFile(MultipartFile file) throws IOException {
        log.info("Upload new file {} and byte {}",file.getName(), file.getBytes().length);
        return cloudinary.uploader()
                .upload(file.getBytes(),
                        Map.of("public_id", UUID.randomUUID().toString()))
                .get("url")
                .toString();
    }
}
