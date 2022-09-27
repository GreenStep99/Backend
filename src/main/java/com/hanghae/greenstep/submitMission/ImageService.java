package com.hanghae.greenstep.submitMission;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.hanghae.greenstep.submitMission.Dto.ImageSizeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;

    public String getImgUrlBase64(String base64) throws IOException {
        String[] strings = base64.split(",");
        String extension = switch (strings[0]) { // check image's extension
            case "data:image/jpeg;base64" -> ".jpeg";
            case "data:image/png;base64" -> ".png";
            case "data:image/webp;base64" -> ".webp";
            default -> ".jpg";
        };
        // convert base64 string to binary data
        byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
        long now = new Date().getTime();
        String fileName = now + "_" + UUID.randomUUID() + extension;
        log.info("작동");
        InputStream img = new ByteArrayInputStream(data);
        Image image = new ImageIcon(img.toString()).getImage(); // 파일 정보 추출
        ImageIcon imageIcon = new ImageIcon(image);
        int imageHeight = image.getHeight(imageIcon.getImageObserver());
        int imageWidth = image.getWidth(imageIcon.getImageObserver());
        log.info(imageHeight+"   "+ imageWidth);
        if (imageHeight > 320 && imageWidth > 320) {
            log.info("문제 발생");
            ImageSizeDto sizeDto = defineSize(imageIcon.getIconHeight(), imageIcon.getIconWidth());
            img = resize(img, sizeDto.getWidth(), sizeDto.getHeight());
            log.info("리사이즈 완료");
        }
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(data.length);
        metadata.setContentType("image/" + extension.substring(1));
        metadata.setCacheControl("public, max-age=31536000");
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, img, metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    public ImageSizeDto defineSize(int height, int width) {
        if (height > width) {
            double ratio = width / 320;
            int resizedHeight = (int) (height / ratio);
            log.info(height +"     "+ width);
            return (new ImageSizeDto(resizedHeight, 320));
        }
        int ratio = height / 320;
        int resizedWidth = width / ratio;
        log.info(height +"     "+ width);
        return (new ImageSizeDto(320, resizedWidth));
    }

    /* 리사이즈 실행 메소드 */
    public static InputStream resize(InputStream inputStream, int width, int height)
            throws IOException {
        log.info("리사이즈 두둥탁");
        BufferedImage inputImage = ImageIO.read(inputStream);  // 받은 이미지 읽기

        BufferedImage outputImage = new BufferedImage(width, height, inputImage.getType());
        // 입력받은 리사이즈 길이와 높이

        Graphics2D graphics2D = outputImage.createGraphics();
        graphics2D.drawImage(inputImage, 0, 0, width, height, null); // 그리기
        graphics2D.dispose(); // 자원해제

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(outputImage, "png", os);                          // Passing: ​(RenderedImage im, String formatName, OutputStream output)

        return new ByteArrayInputStream(os.toByteArray());
    }
}

