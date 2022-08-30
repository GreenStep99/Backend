package com.hanghae.greenstep.member;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshTokenCheck(@RequestBody String nickname, HttpServletRequest request, HttpServletResponse response){
        return memberService.refreshToken(nickname, request, response);
    }
}
