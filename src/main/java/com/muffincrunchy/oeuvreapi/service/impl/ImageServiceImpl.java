package com.muffincrunchy.oeuvreapi.service.impl;

import com.muffincrunchy.oeuvreapi.model.entity.Image;
import com.muffincrunchy.oeuvreapi.repository.ImageRepository;
import com.muffincrunchy.oeuvreapi.service.ImageService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final Path directoryPath;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository, @Value("${oeuvre.multipart.path_location}") String directoryPath) {
        this.imageRepository = imageRepository;
        this.directoryPath = Paths.get(directoryPath);
    }

    @PostConstruct
    public void init() {
        if (!Files.exists(directoryPath)) {
            try {
                Files.createDirectory(directoryPath);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
        }
    }

    @Override
    public Image create(MultipartFile multipartFile) {
        try {
            if (!List.of("image/jpeg", "image/png", "image/jpg").contains(multipartFile.getContentType())) {
                throw new ConstraintViolationException("invalid image type", null);
            }
            String filename = UUID.randomUUID().toString() + "-" + multipartFile.getOriginalFilename();
            Path path = directoryPath.resolve(filename);
            Files.copy(multipartFile.getInputStream(), path);
            Image image = Image.builder()
                    .name(filename)
                    .size(multipartFile.getSize())
                    .contentType(multipartFile.getContentType())
                    .path(path.toString())
                    .build();
            imageRepository.saveAndFlush(image);
            return image;
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public Resource getById(String id) {
        try {
            Image image = imageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "data not found"));
            Path imagePath = Paths.get(image.getPath());
            if (Files.exists(imagePath)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "data not found");
            }
            return new UrlResource(imagePath.toUri());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public void deleteById(String id) {
        try {
            Image image = imageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "data not found"));
            Path imgPath = Paths.get(image.getPath());
            if (!Files.exists(imgPath)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "data not found");
            }
            Files.delete(imgPath);
            imageRepository.delete(image);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
