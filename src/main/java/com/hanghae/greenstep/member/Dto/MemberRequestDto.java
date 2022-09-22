package com.hanghae.greenstep.member.Dto;

import com.hanghae.greenstep.kakaoAPI.PushStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDto {
    private String nickname;

    private String name;

    private String profilePhoto;

    private Boolean acceptMail;

    private PushStatus pushStatus;
    
}
