package com.hanghae.greenstep.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponseDto {
    private Long memberId;
    private String nickname;
    private String name;
    private String email;
    private String profilePhoto;

}
