package com.hanghae.greenstep.member;

import lombok.Getter;

@Getter
public class MemberRequestDto {
    private String nickname;

    private String name;

    private String profilePhoto;

    public MemberRequestDto(){}

    public MemberRequestDto(String nickname, String name, String profilePhoto){
        this.nickname=nickname;
        this.name=name;
        this.profilePhoto=profilePhoto;
    }
}
