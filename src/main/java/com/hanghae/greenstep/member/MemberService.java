package com.hanghae.greenstep.member;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.hanghae.greenstep.exception.CustomException;
import com.hanghae.greenstep.exception.ErrorCode;
import com.hanghae.greenstep.jwt.RefreshToken;
import com.hanghae.greenstep.jwt.RefreshTokenRepository;
import com.hanghae.greenstep.jwt.TokenDto;
import com.hanghae.greenstep.jwt.TokenProvider;
import com.hanghae.greenstep.kakaoLogin.KakaoMemberInfoDto;
import com.hanghae.greenstep.kakaoLogin.KakaoSocialService;
import com.hanghae.greenstep.shared.Check;
import com.hanghae.greenstep.shared.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final Check check;
    private final MemberRepository memberRepository;

    private final KakaoSocialService kakaoSocialService;

    public ResponseEntity<?> refreshToken( HttpServletRequest request, HttpServletResponse response) {
        tokenProvider.validateToken(request.getHeader("Refresh_Token"));
        Member requestingMember = validateMember(request);
        long accessTokenExpire = Long.parseLong(request.getHeader("Access_Token_Expire_Time"));
        long now = (new Date().getTime());
        if (now>accessTokenExpire){
            tokenProvider.deleteRefreshToken(requestingMember);
            throw new CustomException(ErrorCode.INVALID_TOKEN);}

        RefreshToken refreshTokenConfirm = refreshTokenRepository.findByMember(requestingMember).orElseThrow(
                ()-> new CustomException(ErrorCode.REFRESH_TOKEN_IS_EXPIRED));
        if (Objects.equals(refreshTokenConfirm.getValue(), request.getHeader("Refresh_Token"))) {
            TokenDto tokenDto = tokenProvider.generateAccessTokenDto(requestingMember);
            accessTokenToHeaders(tokenDto, response);
            return new ResponseEntity<>(Message.success("ACCESS_TOKEN_REISSUE"), HttpStatus.OK);
        }
        tokenProvider.deleteRefreshToken(requestingMember);
        throw new CustomException(ErrorCode.INVALID_TOKEN);

    }
    public void accessTokenToHeaders(TokenDto tokenDto, HttpServletResponse response) {
        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.addHeader("Access_Token_Expire_Time", tokenDto.getAccessTokenExpiresIn().toString());
    }

    @Transactional
    public ResponseEntity<?> updateMemberInfo(MemberRequestDto memberRequestDto, HttpServletRequest request) {
        Member member = memberRepository.findByEmail(check.accessTokenCheck(request).getEmail()).orElseThrow(
                () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND)
        );
        member.update(memberRequestDto);
        MemberResponseDto memberResponseDto = new MemberResponseDto(member);
    return new ResponseEntity<>(Message.success(memberResponseDto),HttpStatus.OK);
    }

    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh_Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }

    @Transactional(readOnly=true)
    public ResponseEntity<?> getMemberInfo(HttpServletRequest request) {
        Member member =check.accessTokenCheck(request);
        MemberResponseDto memberResponseDto = new MemberResponseDto(member);
        return new ResponseEntity<>(Message.success(memberResponseDto),HttpStatus.OK);
    }


    public KakaoPhotoDto getKakaoPhoto(HttpServletRequest request) throws JsonProcessingException {
        String accessToken = request.getHeader("Kakao_Authorization");
        KakaoMemberInfoDto kakaoMemberInfoDto = kakaoSocialService.getKakaoUserInfo(accessToken);
        KakaoPhotoDto kakaoPhotoDto = new KakaoPhotoDto(kakaoMemberInfoDto.getProfilePhoto());
        return kakaoPhotoDto;
    }
}
