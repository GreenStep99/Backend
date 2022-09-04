package com.hanghae.greenstep.submitMission;

import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.shared.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubmitMissionResponseDto {
    private Long id;
    private Long userId;
    private String profilePhoto;
    private String nickname;
    private String missionName;
    private String missionImgUrl;
    private Status status;
    private String reason;
    private String adminName;

    public SubmitMissionResponseDto(SubmitMission submitMission){
        this.id = submitMission.getId();
        this.userId = submitMission.getMember().getId();
        this.nickname = submitMission.getMember().getNickname();
        this.missionName = submitMission.getMission().getMissionName();
        this.missionImgUrl = submitMission.getImgUrl();
        this.status = submitMission.getStatus();
        this.reason = submitMission.getReason();
        this.adminName = submitMission.getAdminName();

    }
}
