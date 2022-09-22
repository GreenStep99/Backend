package com.hanghae.greenstep.kakaoAPI;

import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FcmService {
    @Value("${pathToGoogleCertificate}")
    private String json;
    public static String getAccessToken() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new FileInputStream("firebase/greenstep-6c162-firebase-adminsdk-utsuy-22bdb126de.json"))
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform","https://www.googleapis.com/auth/firebase.messaging"));
        return googleCredentials.refreshAccessToken().getTokenValue();
    }
}

