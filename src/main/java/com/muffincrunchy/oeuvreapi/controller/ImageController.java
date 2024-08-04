package com.muffincrunchy.oeuvreapi.controller;

import com.muffincrunchy.oeuvreapi.model.entity.Image;
import com.muffincrunchy.oeuvreapi.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.muffincrunchy.oeuvreapi.model.constant.ApiUrl.IMG_URL;
import static com.muffincrunchy.oeuvreapi.model.constant.ApiUrl.USER_URL;

@RequiredArgsConstructor
@RestController
@RequestMapping(IMG_URL)
public class ImageController {

    private ImageService imageService;

    @PostMapping(path = "/test-upload")
    public ResponseEntity<?> testUpload(
            @RequestPart(name = "image") MultipartFile multipartFile
    ){
        Image image = imageService.create(multipartFile, "/uploads");
        return ResponseEntity.status(HttpStatus.CREATED).body(image);
    }
}
