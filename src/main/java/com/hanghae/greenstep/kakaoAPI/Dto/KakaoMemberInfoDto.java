package com.hanghae.greenstep.kakaoAPI.Dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoMemberInfoDto {
    private Long id;
    private String nickname;
    private String email;
    private String profilePhoto;


}
