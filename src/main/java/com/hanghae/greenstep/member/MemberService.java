package com.hanghae.greenstep.member;


import com.hanghae.greenstep.exception.CustomException;
import com.hanghae.greenstep.exception.ErrorCode;
import com.hanghae.greenstep.jwt.RefreshToken;
import com.hanghae.greenstep.jwt.RefreshTokenRepository;
import com.hanghae.greenstep.jwt.TokenDto;
import com.hanghae.greenstep.jwt.TokenProvider;
import com.hanghae.greenstep.shared.Check;
import com.hanghae.greenstep.shared.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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


    public ResponseEntity<?> refreshToken( HttpServletRequest request, HttpServletResponse response) {
        tokenProvider.validateToken(request.getHeader("Refresh-Token"));
        Member requestingMember = validateMember(request);
        long accessTokenExpire = Long.parseLong(request.getHeader("Access-Token-Expire-Time"));
        long now = (new Date().getTime());
        if (now>accessTokenExpire){
            tokenProvider.deleteRefreshToken(requestingMember);
            throw new CustomException(ErrorCode.INVALID_TOKEN);}

        RefreshToken refreshTokenConfirm = refreshTokenRepository.findByMember(requestingMember).orElse(null);
        if (refreshTokenConfirm == null) {
            throw new CustomException(ErrorCode.REFRESH_TOKEN_IS_EXPIRED);
        }
        if (Objects.equals(refreshTokenConfirm.getValue(), request.getHeader("Refresh-Token"))) {
            TokenDto tokenDto = tokenProvider.generateAccessTokenDto(requestingMember);
            accessTokenToHeaders(tokenDto, response);
            return new ResponseEntity<>(Message.success("ACCESS_TOKEN_REISSUE"), HttpStatus.OK);
        } else {
            tokenProvider.deleteRefreshToken(requestingMember);
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }
    public void accessTokenToHeaders(TokenDto tokenDto, HttpServletResponse response) {
        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.addHeader("Access-Token-Expire-Time", tokenDto.getAccessTokenExpiresIn().toString());
    }

    public ResponseEntity<?> updateProfileInfo(MemberRequestDto memberRequestDto, HttpServletRequest request) {
        Member member = check.accessTokenCheck(request);
        member.update(memberRequestDto);
    return new ResponseEntity<>(Message.success(null),HttpStatus.OK);
    }

    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }
}
