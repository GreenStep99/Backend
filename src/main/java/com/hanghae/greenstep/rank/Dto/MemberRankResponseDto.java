package com.hanghae.greenstep.rank.Dto;

import com.hanghae.greenstep.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRankResponseDto {
    private Long memberId;
    private String profilePhoto;
    private String nickName;
    private String name;

    public MemberRankResponseDto(Member member){
        this.memberId =member.getId();
        this.profilePhoto = member.getProfilePhoto();
        this.nickName = member.getNickname();
        this.name = member.getName();
    }
}