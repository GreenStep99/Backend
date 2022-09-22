package com.hanghae.greenstep.kakaoAPI.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PushTokenDto {
    private String uuid;
    private String deviceId;
    private String pushType;
    private String pushToken;
    private Long createdAt;
    private Long updatedAt;

    @Builder
    public PushTokenDto(String uuid, String deviceId, String pushToken, String pushType, Long createdAt, Long updatedAt){
        this.uuid = uuid;
        this.deviceId = deviceId;
        this.pushToken = pushToken;
        this.pushType = pushType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
