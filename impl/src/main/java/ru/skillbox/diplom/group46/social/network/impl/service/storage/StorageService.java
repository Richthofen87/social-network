package ru.skillbox.diplom.group46.social.network.impl.service.storage;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.diplom.group46.social.network.api.dto.storage.StorageDto;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class StorageService {

    private final Cloudinary cloudinary;
    public StorageDto uploadFile(MultipartFile file) throws IOException {
        log.info("Upload new file {} and byte {}",file.getName(), file.getBytes().length);
        var uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));

        var dto = new StorageDto();
        dto.setFileName((String) uploadResult.get("secure_url"));
        return dto;
    }
}
