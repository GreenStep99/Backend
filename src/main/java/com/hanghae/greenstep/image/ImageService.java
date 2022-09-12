package com.hanghae.greenstep.image;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.hanghae.greenstep.exception.CustomException;
import com.hanghae.greenstep.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    AmazonS3Client amazonS3Client;

    @Autowired
    public ImageService(AmazonS3Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    public String getImgUrl(MultipartFile multipartFile) {
        // forEach 구문을 통해 multipartFile로 넘어온 파일들 하나씩 fileNameList에 추가
        String originalName = UUID.randomUUID() + multipartFile.getOriginalFilename(); // 파일 이름
        long size = multipartFile.getSize();  // 파일 크기
        String type = multipartFile.getContentType();
        if (!Objects.requireNonNull(type).startsWith("image")) throw new CustomException(ErrorCode.FILE_TYPE_INVALID);
        if (size > 3500000) throw new CustomException(ErrorCode.FILE_SIZE_INVALID);

        ObjectMetadata objectMetaData = new ObjectMetadata();
        objectMetaData.setContentType(multipartFile.getContentType());
        objectMetaData.setContentLength(size);

        // S3에 업로드
        try {
            amazonS3Client.putObject(
                    new PutObjectRequest(bucket, originalName, multipartFile.getInputStream(), objectMetaData)
                            .withCannedAcl(CannedAccessControlList.PublicRead)
            );
        } catch (IOException e) {
            throw new CustomException(ErrorCode.FILE_TYPE_INVALID);
        }

        return amazonS3Client.getUrl(bucket, originalName).toString();
    }

    public String getImgUrlBase64(String base64) throws IOException {
        String[] strings = base64.split(",");
        String extension = switch (strings[0]) { // check image's extension
            case "data:image/jpeg;base64" -> ".jpeg";
            case "data:image/png;base64" -> ".png";
            default -> // should write cases for more images types
                    ".jpg";
        };
        // convert base64 string to binary data
        byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
        long now = new Date().getTime();
        String fileName = UUID.randomUUID().toString() + now + extension;
        InputStream img = new ByteArrayInputStream(data);
        Image image = new ImageIcon(img.toString()).getImage(); // 파일 정보 추출
        ImageIcon imageIcon = new ImageIcon(image);
        /* 파일의 길이 혹은 세로길이에 따라 if(분기)를 통해서 응용할 수 있습니다.
         * '예를 들어 파일의 가로 해상도가 1000이 넘을 경우 1000으로 리사이즈 한다. 같은 분기' */
        if (imageIcon.getIconHeight() > 1280 || imageIcon.getIconWidth() > 800) {
            int width = 800; // 리사이즈할 가로 길이
            int height = 1280; // 리사이즈할 세로 길이
            // 리사이즈 실행 메소드에 값을 넘겨준다.
            img= resize(img, width, height);
        }
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(data.length);
        metadata.setContentType("image/" + extension.substring(1));
        metadata.setCacheControl("public, max-age=31536000");
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, img, metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }


    /* 리사이즈 실행 메소드 */
    public static InputStream resize(InputStream inputStream, int width, int height)
            throws IOException {

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

