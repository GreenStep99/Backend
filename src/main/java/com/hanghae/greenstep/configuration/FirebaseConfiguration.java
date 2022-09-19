package com.hanghae.greenstep.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class FirebaseConfiguration {
    private final Logger logger = LoggerFactory.getLogger(FirebaseConfiguration.class);

    FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.getApplicationDefault())
            .setDatabaseUrl("firebase-adminsdk-utsuy@greenstep-6c162.iam.gserviceaccount.com/")
            .build();


    // This registration token comes from the client FCM SDKs.
    String registrationToken = "YOUR_REGISTRATION_TOKEN";

    // See documentation on defining a message payload.
    Message message = Message.builder()
            .putData("score", "850")
            .putData("time", "2:45")
            .setToken(registrationToken)
            .build();

    // Send a message to the device corresponding to the provided
// registration token.
    String response = FirebaseMessaging.getInstance().send(message);

    public FirebaseConfiguration() throws FirebaseMessagingException, IOException {
    }
}
