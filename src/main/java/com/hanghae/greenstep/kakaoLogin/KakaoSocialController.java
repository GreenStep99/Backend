package com.hanghae.greenstep.kakaoLogin;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.hanghae.greenstep.jwt.TokenDto;
import com.hanghae.greenstep.shared.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequiredArgsConstructor
public class KakaoSocialController {

    private final KakaoSocialService kakaoSocialService;

    @Transactional
    @GetMapping("/users/kakao/callback")
    public ResponseEntity<?> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        TokenDto tokenDto= kakaoSocialService.kakaoLogin(code);
        tokenDto.tokenToHeaders(response);
        LoginResponseDto loginResponseDto = kakaoSocialService.loginInfo(tokenDto);
        return new ResponseEntity<>(Message.success(loginResponseDto), HttpStatus.OK);
    }

    @GetMapping("/kakao/logout")
    public ResponseEntity<?> kakaoLogout(HttpServletRequest request) throws JsonProcessingException {
        return kakaoSocialService.kakaoLogout(request);
    }
    @PostMapping("/kakao/unregister")
    public ResponseEntity<?> deleteMemberInfo(HttpServletRequest request) throws JsonProcessingException {
        return kakaoSocialService.deleteMemberInfo(request);
    }
}