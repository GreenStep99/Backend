package com.hanghae.greenstep.kakaoAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae.greenstep.kakaoAPI.Dto.CustomFieldDto;
import com.hanghae.greenstep.kakaoAPI.Dto.PushContentDto;
import com.hanghae.greenstep.kakaoAPI.Dto.PushMsgDto;
import com.hanghae.greenstep.kakaoAPI.Dto.PushTokenDto;
import com.hanghae.greenstep.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoPushAlertService {
    private final FcmService fcmService;

    @Value("${kakao_admin_key}")
    String adminKey;


    public void requestPushToken(Member member) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","KakaoAK "+ adminKey);
        headers.add("Content-type", "application/x-www-form-urlencoded");
        log.info("달리다");
        String pushToken = FcmService.getAccessToken();
        log.info(pushToken);
        String deviceId = UUID.randomUUID().toString();
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("uuid", member.getKakaoId().toString());
        body.add("device_id", deviceId);
        body.add("push_type", "fcm");
        body.add("push_token", pushToken);
        log.info("통과");
        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, Object>> kakaoPushTokenRequest =
                new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/push/register",
                HttpMethod.POST,
                kakaoPushTokenRequest,
                String.class
        );
        String responseBody = response.getBody();
        log.info(responseBody);
        log.info("푸시토큰 발급 완료");
    }

    @Transactional
    public void sendPushAlert(Member member, CustomFieldDto customFieldDto, PushContentDto pushContentDto) throws JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","KakaoAK "+ adminKey);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        PushTokenDto pushTokenDto = getPushToken(member, headers);

        List<String> uuids = new ArrayList<>();
        uuids.add(pushTokenDto.getUuid());
        PushMsgDto pushMsgDto= PushMsgDto.builder()
                .dryRun(true)
                .customFieldDto(customFieldDto)
                .pushContentDto(pushContentDto)
                .build();
        MultiValueMap<String, Object> pushContentBody = new LinkedMultiValueMap<>();
        pushContentBody.add("uuids", uuids);
        pushContentBody.add("push_message", pushMsgDto);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, Object>> kakaoPushTokenRequest =
                new HttpEntity<>(pushContentBody, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/push/tokens",
                HttpMethod.GET,
                kakaoPushTokenRequest,
                String.class
        );
    }

    @Transactional
    public void deletePushToken(Member member) throws JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","KakaoAK "+ adminKey);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        PushTokenDto pushTokenDto = getPushToken(member, headers);

        MultiValueMap<String, Object> tokenDeleteBody = new LinkedMultiValueMap<>();
        tokenDeleteBody.add("uuid", member.getKakaoId());
        tokenDeleteBody.add("device_id", pushTokenDto.getDeviceId());
        tokenDeleteBody.add("push_type", pushTokenDto.getPushType());

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, Object>> kakaoPushTokenDelete =
                new HttpEntity<>(tokenDeleteBody, headers);
        RestTemplate requestDeleteRT = new RestTemplate();
        ResponseEntity<String> responseDeleteRT = requestDeleteRT.exchange(
                "https://kapi.kakao.com/v2/push/deregister",
                HttpMethod.POST,
                kakaoPushTokenDelete,
                String.class
        );
        log.info(responseDeleteRT.getBody());
    }

    public PushTokenDto getPushToken(Member member, HttpHeaders headers) throws JsonProcessingException {
        log.info("푸시토큰 가져오기");
        MultiValueMap<String, Object> tokenCheckBody = new LinkedMultiValueMap<>();
        tokenCheckBody.add("uuid", member.getKakaoId());

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, Object>> kakaoPushTokenRequest =
                new HttpEntity<>(tokenCheckBody, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/push/tokens",
                HttpMethod.GET,
                kakaoPushTokenRequest,
                String.class
        );
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        String uuid = jsonNode.get("uuid").asText();
        String deviceId = jsonNode.get("device_id").asText();
        String pushToken = jsonNode.get("push_token").asText();
        String pushType = jsonNode.get("push_type").asText();
        Long createdAt = jsonNode.get("created_at").asLong();
        Long updatedAt = jsonNode.get("updated_at").asLong();
        return PushTokenDto.builder()
                .uuid(uuid)
                .deviceId(deviceId)
                .pushToken(pushToken)
                .pushType(pushType)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
