package com.hanghae.greenstep.shared.scheduler;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.hanghae.greenstep.submitMission.SubmitMission;
import com.hanghae.greenstep.submitMission.SubmitMissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.hanghae.greenstep.shared.Status.REJECTED;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImageScheduler {

    private final SubmitMissionRepository submitMissionRepository;
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.base_url}")
    String baseUrl;

    @Scheduled(cron = "0 0 0 1 * *")
    @Transactional
    public void deleteImages() {
        List<SubmitMission> submitMissionList = submitMissionRepository.findAllByStatus(REJECTED);
        ObjectListing uploadedImageList = amazonS3Client.listObjects(bucket, "submit_mission/");
        List<S3ObjectSummary> imageList = uploadedImageList.getObjectSummaries();
        long now = new Date().getTime();
        for (S3ObjectSummary s3ObjectSummaries : imageList) {
            if (Objects.equals(s3ObjectSummaries.getKey().split("/")[0], "submit_mission") ||
                    s3ObjectSummaries.getKey().split("/").length > 1)
                for (SubmitMission submitMission : submitMissionList) {
                    if (Objects.equals(submitMission.getImgUrl().split("/")[3], "submit")) {
                        long created = Long.parseLong(submitMission.getImgUrl().split("[￦_/]")[5]);
                        if (now - created >= 1209600000) {
                            amazonS3Client.deleteObject(bucket, s3ObjectSummaries.getKey());
                        }
                    }
                }
        }
    }
}
