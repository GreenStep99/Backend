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

import java.io.IOException;
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

    public String getImgUrl(MultipartFile multipartFile){
        // forEach 구문을 통해 multipartFile로 넘어온 파일들 하나씩 fileNameList에 추가
        String originalName = UUID.randomUUID()+multipartFile.getOriginalFilename(); // 파일 이름
        long size = multipartFile.getSize();  // 파일 크기
        String type = multipartFile.getContentType();
        if (!type.startsWith("image")) throw new CustomException(ErrorCode.FILE_TYPE_INVALID);
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
}
