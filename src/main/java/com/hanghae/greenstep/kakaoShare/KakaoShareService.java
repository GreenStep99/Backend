package com.hanghae.greenstep.kakaoShare;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae.greenstep.exception.CustomException;
import com.hanghae.greenstep.exception.ErrorCode;
import com.hanghae.greenstep.feed.Feed;
import com.hanghae.greenstep.feed.FeedRepository;
import com.hanghae.greenstep.kakaoShare.Dto.ButtonsDto;
import com.hanghae.greenstep.kakaoShare.Dto.ContentDto;
import com.hanghae.greenstep.kakaoShare.Dto.KakaoTemplateDto;
import com.hanghae.greenstep.kakaoShare.Dto.SocialDto;
import com.hanghae.greenstep.shared.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KakaoShareService {

    private final FeedRepository feedRepository;

    public ResponseEntity<?> shareKakaoToME(Long feedId, HttpServletRequest request) throws JsonProcessingException {
        String accessToken = request.getHeader("Kakao_Authorization");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        Feed feed = feedRepository.findById(feedId).orElseThrow( () -> new CustomException(ErrorCode.FEED_NOT_FOUND));
        KakaoTemplateDto kakaoTemplateDto = boxKakaoTemplate(feed,null);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("template_object", kakaoTemplateDto);
        // HTTP 요청 보내기
         HttpEntity<MultiValueMap<String, Object>> kakaoShareRequest = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/api/talk/memo/default/send",
                HttpMethod.POST,
                kakaoShareRequest,
                String.class
        );
        return getResponseEntity(response);
    }


    public ResponseEntity<?> shareKakaoToFriends(Long feedId, List<String> kakaoFriends,HttpServletRequest request) throws JsonProcessingException {
        String accessToken = request.getHeader("Kakao_Authorization");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        Feed feed = feedRepository.findById(feedId).orElseThrow( () -> new CustomException(ErrorCode.FEED_NOT_FOUND));
        KakaoTemplateDto kakaoTemplateDto = boxKakaoTemplate(feed,kakaoFriends);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("receiver_uuids", kakaoFriends);
        body.add("template_object", kakaoTemplateDto);

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
        return getResponseEntity(response);
    }

    private ResponseEntity<?> getResponseEntity(ResponseEntity<String> response) throws JsonProcessingException {
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        int result = jsonNode.get("result_code").asInt();
        if(result == 1)throw new CustomException(ErrorCode.SHARE_FAILURE);
        return new ResponseEntity<>(Message.success("카카오톡 메세지가 성공적으로 전달되었습니다."), HttpStatus.OK);
    }

    public KakaoTemplateDto boxKakaoTemplate(Feed feed, List<String> friendList){
        List<ButtonsDto> buttons= new ArrayList<>();
        ButtonsDto buttonsDto = new ButtonsDto();
        buttons.add(buttonsDto);
        SocialDto socialDto = new SocialDto(feed.getClapCount());
        ContentDto contentDto = new ContentDto(feed.getImgUrl(),feed.getContent());
        return KakaoTemplateDto.builder()
                .buttons(buttons)
                .social(socialDto)
                .friendIdList(friendList)
                .contentDto(contentDto)
                .build();
    }
}
