package com.hanghae.greenstep.kakaoLogin;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponseDto {
    private Long memberId;

    private String nickname;

    private String profilePhoto;

    private Boolean newComer;

    @Builder
    public LoginResponseDto(Long memberId, String nickname, String profilePhoto, Boolean newComer) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.profilePhoto = profilePhoto;
        this.newComer = newComer;
    }
}
