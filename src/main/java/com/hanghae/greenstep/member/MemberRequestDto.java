package com.hanghae.greenstep.member;

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
    
}
