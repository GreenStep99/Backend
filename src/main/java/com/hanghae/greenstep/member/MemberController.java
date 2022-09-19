package com.hanghae.greenstep.member;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.hanghae.greenstep.shared.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        MemberResponseDto memberResponseDto = memberService.updateMemberInfo(memberRequestDto,request);
        return new ResponseEntity<>(Message.success(memberResponseDto),HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshTokenCheck(HttpServletRequest request, HttpServletResponse response){
        memberService.refreshToken(request, response);
        return new ResponseEntity<>(Message.success("ACCESS_TOKEN_REISSUE"), HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity<?> getProfileInfo(HttpServletRequest request){
        MemberResponseDto memberResponseDto = memberService.getMemberInfo(request);
        return new ResponseEntity<>(Message.success(memberResponseDto),HttpStatus.OK);
    }

    @GetMapping("/kakaoProfilePhoto")
    public ResponseEntity<?> getKakaoPhoto(HttpServletRequest request) throws JsonProcessingException {
        KakaoPhotoDto kakaoPhotoDto = memberService.getKakaoPhoto(request);
        return new ResponseEntity<>(Message.success(kakaoPhotoDto), HttpStatus.OK);
    }

}
