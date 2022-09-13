package com.hanghae.greenstep.shared;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae.greenstep.configuration.RedisConfiguration;
import com.hanghae.greenstep.exception.CustomException;
import com.hanghae.greenstep.exception.ErrorCode;
import com.hanghae.greenstep.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FcmService {

    private final Check check;

    private final RedisTemplate<String, String> redisTemplate;

    @Value("${kakao_admin_key}")
    String adminKey;


    public ResponseEntity<?> pushAlert(HttpServletRequest request) throws JsonProcessingException {
        String accessToken = "KakaoAK " + adminKey;
        Member member = check.accessTokenCheck(request);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        String deviceId = member.getId() + UUID.randomUUID().toString();

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("uuids", member.getKakaoId());
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
        redisTemplate.convertAndSend(member.getId().toString(),pushToken);
        return new ResponseEntity<>(Message.success("푸시토큰 발급 완료"), HttpStatus.OK);
    }


}
