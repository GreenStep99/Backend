package com.hanghae.greenstep.kakaoAPI;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

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

