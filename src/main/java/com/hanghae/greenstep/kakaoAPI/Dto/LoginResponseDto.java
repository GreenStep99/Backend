package com.hanghae.greenstep.kakaoAPI.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponseDto {
    private Long memberId;

    private String name;

    private String profilePhoto;

    private Boolean newComer;

    @Builder
    public LoginResponseDto(Long memberId, String name, String profilePhoto, Boolean newComer) {
        this.memberId = memberId;
        this.name = name;
        this.profilePhoto = profilePhoto;
        this.newComer = newComer;
    }
}
