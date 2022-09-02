package com.hanghae.greenstep.admin;

import com.hanghae.greenstep.admin.Admin;
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
public class AdminTokenDto {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;
    private Admin admin;

    public void tokenToHeaders(HttpServletResponse response) {
        response.addHeader("Authorization", "Bearer " + getAccessToken());
        response.addHeader("Refresh_Token", getRefreshToken());
        response.addHeader("Access_Token_Expire_Time", getAccessTokenExpiresIn().toString());
    }
}
