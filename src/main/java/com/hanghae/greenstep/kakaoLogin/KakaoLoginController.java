package com.hanghae.greenstep.kakaoLogin;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.hanghae.greenstep.jwt.TokenDto;
import com.hanghae.greenstep.shared.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequiredArgsConstructor
public class KakaoLoginController {

    private final KakaoLoginService kakaoLoginService;

    @Transactional
    @GetMapping("/users/kakao/callback")
    public ResponseEntity<?> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        TokenDto tokenDto= kakaoLoginService.kakaoLogin(code);
        tokenDto.tokenToHeaders(response);
        LoginResponseDto loginResponseDto = kakaoLoginService.loginInfo(tokenDto);
        return new ResponseEntity<>(Message.success(loginResponseDto), HttpStatus.OK);
    }
}