package com.hanghae.greenstep.shared.pushAlert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.shared.Check;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class FcmService {

    private final Check check;

    private final RedisTemplate<String, String> redisTemplate;

    @Value("${kakao_admin_key}")
    String adminKey;


    public void requestPushToken(Member member) throws JsonProcessingException {
        String accessToken = "KakaoAK " + adminKey;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        String deviceId = member.getId() + UUID.randomUUID().toString();

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("uuid", member.getKakaoId());
        body.add("device_id", deviceId);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, Object>> kakaoPushTokenRequest =
                new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/push/tokens",
                HttpMethod.POST,
                kakaoPushTokenRequest,
                String.class
        );
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        String pushToken = jsonNode.get("push_Token").asText();
        String push_type = jsonNode.get("push_type").asText();
        log.info("푸시토큰 발급 완료");
    }

    public void sendPushAlert(Member member){
        String accessToken = "KakaoAK " + adminKey;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        List<String> uuids = new ArrayList<>();
        uuids.add(member.getKakaoId().toString());
        PushContentDto pushContentDto =new PushContentDto();

        MultiValueMap<String, Object> pushContentBody = new LinkedMultiValueMap<>();
        pushContentBody.add("uuids", uuids);
        pushContentBody.add("push_message", pushContentDto);

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

    public void deletePushToken(Member member) throws JsonProcessingException {
        String accessToken = "KakaoAK " + adminKey;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

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
        String deviceId = jsonNode.get("device_id").asText();
        String pushType = jsonNode.get("push_type").asText();

        MultiValueMap<String, Object> tokenDeleteBody = new LinkedMultiValueMap<>();
        tokenDeleteBody.add("uuid", member.getKakaoId());
        tokenDeleteBody.add("device_id", deviceId);
        tokenDeleteBody.add("push_type", pushType);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, Object>> kakaoPushTokenDelete =
                new HttpEntity<>(tokenDeleteBody, headers);
        RestTemplate requestDeleteRT = new RestTemplate();
        ResponseEntity<String> responseDeleteRT = rt.exchange(
                "https://kapi.kakao.com/v2/push/deregister",
                HttpMethod.POST,
                kakaoPushTokenRequest,
                String.class
        );
        log.info(responseDeleteRT.getBody());
    }

}
