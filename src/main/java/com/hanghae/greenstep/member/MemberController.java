package com.hanghae.greenstep.member;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class MemberController {

    private final MemberService memberService;

    @PatchMapping("/info")
    public ResponseEntity<?> updateMemberInfo(@RequestBody MemberRequestDto memberRequestDto,HttpServletRequest request){
        return memberService.updateMemberInfo(memberRequestDto,request);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshTokenCheck(HttpServletRequest request, HttpServletResponse response){
        return memberService.refreshToken(request, response);
    }

    @GetMapping("/info")
    public ResponseEntity<?> getProfileInfo(HttpServletRequest request){
        return memberService.getMemberInfo(request);
    }


}
