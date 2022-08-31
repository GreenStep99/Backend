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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;

    private final RefreshTokenRepository refreshTokenRepository;

    private final Check check;


    public void tokenToHeaders(TokenDto tokenDto, HttpServletResponse response) {
        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.addHeader("Refresh-Token", tokenDto.getRefreshToken());
        response.addHeader("Access-Token-Expire-Time", tokenDto.getAccessTokenExpiresIn().toString());
    }

    public ResponseEntity<?> refreshToken(String nickname, HttpServletRequest request, HttpServletResponse response) {
        if (null == request.getHeader("Refresh-Token")) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        Member requestingMember = memberRepository.findByNickname(nickname).orElse(null);
        if (requestingMember == null) {
            throw new CustomException(ErrorCode.INVALID_MEMBER_INFO);
        }
        RefreshToken refreshTokenConfirm = refreshTokenRepository.findByMember(requestingMember).orElse(null);
        if (refreshTokenConfirm == null) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        LocalDateTime currentDateTime = LocalDateTime.now();
        if (!refreshTokenConfirm.getCreatedAt().plusHours(3).equals(currentDateTime)) {
            throw new CustomException(ErrorCode.REFRESH_TOKEN_IS_EXPIRED);
        }//고려
        if (Objects.equals(refreshTokenConfirm.getValue(), request.getHeader("Refresh-Token"))) {
            TokenDto tokenDto = tokenProvider.generateTokenDto(requestingMember);
            accessTokenToHeaders(tokenDto, response);
            return new ResponseEntity<>(Message.success("ACCESS_TOKEN_REISSUE"), HttpStatus.OK);
        } else {
            tokenProvider.deleteRefreshToken(memberRepository.findByNickname(nickname).orElseThrow(
                    () -> new CustomException(ErrorCode.INVALID_TOKEN))
            );
            throw new CustomException(ErrorCode.INVALID_MEMBER_INFO);
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


}
