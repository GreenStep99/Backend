package com.hanghae.greenstep.kakaoAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae.greenstep.exception.CustomException;
import com.hanghae.greenstep.exception.ErrorCode;
import com.hanghae.greenstep.feed.Feed;
import com.hanghae.greenstep.feed.FeedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoShareService {

    private final FeedRepository feedRepository;

    public void shareKakaoToME(Long feedId, HttpServletRequest request) throws JsonProcessingException {
        log.info("돈다");
        String accessToken = request.getHeader("Kakao_Authorization");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        log.info("헤더 생성완료");
        Feed feed = feedRepository.findById(feedId).orElseThrow( () -> new CustomException(ErrorCode.FEED_NOT_FOUND));
        log.info("곧 된다");
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, Object>> kakaoShareRequest = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/api/talk/memo/scrap/send" ,
                HttpMethod.POST,
                kakaoShareRequest,
                String.class
        );
        log.info("거의 성공");
        sendKakaoMessage(response);
    }


    public void shareKakaoToFriends(Long feedId, String[] kakaoFriends,HttpServletRequest request) throws JsonProcessingException {
        String accessToken = request.getHeader("Kakao_Authorization");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        Feed feed = feedRepository.findById(feedId).orElseThrow( () -> new CustomException(ErrorCode.FEED_NOT_FOUND));

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("receiver_uuids", kakaoFriends);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, Object>> kakaoShareRequest =
                new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v1/api/talk/friends/message/default/send",
                HttpMethod.POST,
                kakaoShareRequest,
                String.class
        );
        sendKakaoMessage(response);
    }

    private void sendKakaoMessage(ResponseEntity<String> response) throws JsonProcessingException {
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        int result = jsonNode.get("result_code").asInt();
        if(result == 1)throw new CustomException(ErrorCode.SHARE_FAILURE);
    }

}
