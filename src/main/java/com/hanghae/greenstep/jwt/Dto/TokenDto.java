package com.hanghae.greenstep.jwt.Dto;

import com.hanghae.greenstep.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletResponse;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDto {
    private String grantType;
    private String accessToken;
    private Long accessTokenExpiresIn;
    private Member member;
    private Boolean newComer;
    private String kakaoAccessToken;

    public void tokenToHeaders(HttpServletResponse response) {
        response.addHeader("Authorization", "Bearer " + getAccessToken());
        response.addHeader("Access_Token_Expire_Time", getAccessTokenExpiresIn().toString());
        response.addHeader("Kakao_Authorization", getKakaoAccessToken());
    }
}
