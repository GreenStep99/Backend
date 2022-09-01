package com.hanghae.greenstep.kakaoLogin;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoMemberInfoDto {
    private Long id;
    private String nickname;
    private String name;
    private String email;
    private String profilePhoto;


}
