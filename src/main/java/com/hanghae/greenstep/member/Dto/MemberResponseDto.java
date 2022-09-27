package com.hanghae.greenstep.member.Dto;

import com.hanghae.greenstep.member.Member;
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
    private Long missionPoint;
    private Long dailyMissionPoint;
    private int missionCount;
    private int waitingMissionCount;

    public MemberResponseDto(Member member, int missionCount, int waitingMissionCount) {
        this.memberId = member.getId();
        this.nickname = member.getNickname();
        this.name = member.getName();
        this.email = member.getEmail();
        this.profilePhoto = member.getProfilePhoto();
        this.missionPoint = member.getMissionPoint();
        this.dailyMissionPoint = member.getDailyMissionPoint();
        this.missionCount = missionCount;
        this.waitingMissionCount = waitingMissionCount;
    }
    public MemberResponseDto(Member member) {
        this.memberId = member.getId();
        this.nickname = member.getNickname();
        this.name = member.getName();
        this.email = member.getEmail();
        this.profilePhoto = member.getProfilePhoto();
        this.missionPoint = member.getMissionPoint();
        this.dailyMissionPoint = member.getDailyMissionPoint();
    }

}
