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
        byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);

        InputStream imgStream = new ByteArrayInputStream(data);
        InputStream resizedImg = checkImage(imgStream);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(resizedImg.available());
        metadata.setContentType("image/" + extension.substring(1));
        metadata.setCacheControl("public, max-age=31536000");

        long now = new Date().getTime();
        String fileName = "submit_mission/" + now + "_" + UUID.randomUUID() + extension;
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, resizedImg, metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    public InputStream checkImage(InputStream imgStream) throws IOException {
        BufferedImage inputImage = ImageIO.read(imgStream);
        int imageHeight = inputImage.getHeight(null);
        int imageWidth = inputImage.getWidth(null);
        if (imageHeight > 320 && imageWidth > 320) return resize(inputImage, imageWidth, imageHeight);
        else {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            ImageIO.write(inputImage,"png",outStream);
            return new ByteArrayInputStream(outStream.toByteArray());
        }
    }
    /* 리사이즈 실행 메소드 */
    public InputStream resize(BufferedImage inputImage, int width, int height) throws IOException {

        ImageSizeDto sizeDto = defineSize(height, width);
        Image resizedImage = inputImage.getScaledInstance(sizeDto.getWidth(),sizeDto.getHeight(),Image.SCALE_SMOOTH);

        BufferedImage outputImage = new BufferedImage(sizeDto.getWidth(), sizeDto.getHeight(), inputImage.getType());

        Graphics graphics2D = outputImage.getGraphics();
        graphics2D.drawImage(resizedImage, 0, 0, null); // 그리기
        graphics2D.dispose(); // 자원해제

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(outputImage, "png", os);
        return new ByteArrayInputStream(os.toByteArray());
    }

    public ImageSizeDto defineSize(int height, int width) {
        if (height > width) {
            double ratio = (double)width / 320;
            int resizedHeight = (int) (height / ratio);
            return (new ImageSizeDto(resizedHeight, 320));
        }
        int ratio = height / 320;
        int resizedWidth = width / ratio;
        return (new ImageSizeDto(320, resizedWidth));
    }


}

