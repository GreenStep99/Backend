package com.hanghae.greenstep.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoPhotoDto {

    private String kakaoProfilePhoto;

    public KakaoPhotoDto(String profilePhoto) {
        this.kakaoProfilePhoto = profilePhoto;
    }
}
