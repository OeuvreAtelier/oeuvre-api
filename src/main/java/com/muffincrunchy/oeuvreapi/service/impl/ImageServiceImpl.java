package com.muffincrunchy.oeuvreapi.service.impl;

import com.muffincrunchy.oeuvreapi.model.entity.Image;
import com.muffincrunchy.oeuvreapi.repository.ImageRepository;
import com.muffincrunchy.oeuvreapi.service.ImageService;
import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.exceptions.*;
import io.imagekit.sdk.models.FileCreateRequest;
import io.imagekit.sdk.models.results.Result;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ImageKit imageKit;

    @Override
    public Image create(MultipartFile multipartFile, String directoryPath) {
        try {
            if (!List.of("image/jpeg", "image/png", "image/jpg").contains(multipartFile.getContentType())) {
                throw new ConstraintViolationException("invalid image type", null);
            }
            byte[] fileBytes = multipartFile.getBytes();
            String base64File = Base64.getEncoder().encodeToString(fileBytes);
            String filename = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
            FileCreateRequest request = new FileCreateRequest(base64File, filename);
            request.setFolder(directoryPath);
            Result result = imageKit.upload(request);
            if (result != null && result.getUrl() != null) {
                Image image = Image.builder()
                        .name(result.getName())
                        .size(multipartFile.getSize())
                        .contentType(multipartFile.getContentType())
                        .path(result.getUrl())
                        .fileId(result.getFileId())
                        .build();
                return imageRepository.saveAndFlush(image);
            } else {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "failed to upload image", null);
            }
        } catch (IOException | ForbiddenException | TooManyRequestsException | InternalServerException | UnauthorizedException | BadRequestException |UnknownException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public Image getById(String id) {
        return imageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "data not found"));
    }

    @Override
    public void deleteById(String id) {
        try {
            Image image = imageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "data not found"));
            String fileId = image.getFileId();
            imageRepository.delete(image);
            imageKit.deleteFile(fileId);
        } catch (ForbiddenException | TooManyRequestsException | InternalServerException | UnauthorizedException | BadRequestException |UnknownException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
