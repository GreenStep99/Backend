package com.hanghae.greenstep.configuration;


import com.google.api.client.util.Key;
import com.google.common.collect.ImmutableMap;
import com.google.firebase.internal.NonNull;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushFcmOptions;
import com.google.firebase.messaging.WebpushNotification;

import java.util.HashMap;
import java.util.Map;


public class WebpushConfiguration {

    @Key("headers")
    private final Map<String, String> headers;

    @Key("data")
    private final Map<String, String> data;

    @Key("notification")
    private final Map<String, Object> notification;

    @Key("fcm_options")
    private final WebpushFcmOptions fcmOptions;

    private WebpushConfiguration(Builder builder) {
        this.headers = builder.headers.isEmpty() ? null : ImmutableMap.copyOf(builder.headers);
        this.data = builder.data.isEmpty() ? null : ImmutableMap.copyOf(builder.data);
        this.notification = builder.notification != null ? builder.notification.getField() : null;
        this.fcmOptions = builder.fcmOptions;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Map<String, String> headers = new HashMap<>();
        private final Map<String, String> data = new HashMap<>();
        private WebpushNotification notification;
        private WebpushFcmOptions fcmOptions;


        private Builder() {}

        public Builder putHeader(@NonNull String key, @NonNull String value) {
            headers.put(key, value);
            return this;
        }


        public Builder putAllHeaders(@NonNull Map<String, String> map) {
            headers.putAll(map);
            return this;
        }


        public Builder putData(String key, String value) {
            data.put(key, value);
            return this;
        }


        public Builder putAllData(Map<String, String> map) {
            data.putAll(map);
            return this;
        }


        public Builder setNotification(WebpushNotification notification) {
            this.notification = notification;
            return this;
        }

        public Builder setFcmOptions(WebpushFcmOptions fcmOptions) {
            this.fcmOptions = fcmOptions;
            return this;
        }

        public WebpushConfig build() {
            return new WebpushConfiguration(this);
        }
    }
}
