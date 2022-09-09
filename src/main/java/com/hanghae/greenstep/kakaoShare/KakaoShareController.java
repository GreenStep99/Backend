package com.hanghae.greenstep.kakaoShare;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class KakaoShareController {

    private final KakaoShareService kakaoShareService;

    @PostMapping("/kakao-share/me/{feedId}")
    public ResponseEntity<?> getKakaoShareToMe(@PathVariable Long feedId, HttpServletRequest request) throws JsonProcessingException {
        return kakaoShareService.shareKakaoToME(feedId, request);
    }

    @GetMapping("/kakao-share/friends/{feedId}")
    public ResponseEntity<?> getKakaoShareToFriends(@PathVariable Long feedId, @RequestBody String[] kakaoFriends, HttpServletRequest request) throws JsonProcessingException {
        return kakaoShareService.shareKakaoToFriends(feedId, kakaoFriends, request);
    }

}
