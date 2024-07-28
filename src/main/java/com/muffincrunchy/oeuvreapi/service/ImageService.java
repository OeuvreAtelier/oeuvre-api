package com.muffincrunchy.oeuvreapi.service;

import com.muffincrunchy.oeuvreapi.model.entity.Image;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    Image create(MultipartFile multipartFile, String directoryPath);
    Image getById(String id);
    void deleteById(String id);
}
