package com.hanghae.greenstep.image;

import com.hanghae.greenstep.shared.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/image-upload")
    public ResponseEntity<?> upload(@RequestPart("image") MultipartFile multipartFile) throws IOException {
        return new ResponseEntity<>(Message.success(imageService.getImgUrl(multipartFile)), HttpStatus.OK);
    }
}
