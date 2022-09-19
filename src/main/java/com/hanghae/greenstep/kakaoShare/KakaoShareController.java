package com.hanghae.greenstep.kakaoShare;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hanghae.greenstep.shared.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class KakaoShareController {

    private final KakaoShareService kakaoShareService;

    @PostMapping("/kakao-share/me/{feedId}")
    public ResponseEntity<?> getKakaoShareToMe(@PathVariable Long feedId, HttpServletRequest request) throws JsonProcessingException {
        kakaoShareService.shareKakaoToME(feedId, request);
        return new ResponseEntity<>(Message.success("카카오톡 메세지가 성공적으로 전달되었습니다."), HttpStatus.OK);

    }

    @GetMapping("/kakao-share/friends/{feedId}")
    public ResponseEntity<?> getKakaoShareToFriends(@PathVariable Long feedId, @RequestBody String[] kakaoFriends, HttpServletRequest request) throws JsonProcessingException {
        kakaoShareService.shareKakaoToFriends(feedId, kakaoFriends, request);
        return new ResponseEntity<>(Message.success("카카오톡 메세지가 성공적으로 전달되었습니다."), HttpStatus.OK);
    }

}
