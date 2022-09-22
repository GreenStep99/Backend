package com.hanghae.greenstep.submitMission;

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

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class ImageService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;

    public String getImgUrlBase64(String base64){
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
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(data.length);
        metadata.setContentType("image/"+extension.substring(1));
        metadata.setCacheControl("public, max-age=31536000");
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, img, metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }
}
