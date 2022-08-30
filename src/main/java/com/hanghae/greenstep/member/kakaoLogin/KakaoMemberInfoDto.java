package com.hanghae.greenstep.member.kakaoLogin;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoMemberInfoDto {
    private final Long id;
    private final String nickname;
    private final String name;
    private final String email;
    private final String profilePhoto;


}
