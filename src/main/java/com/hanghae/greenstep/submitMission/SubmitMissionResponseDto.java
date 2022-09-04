package com.hanghae.greenstep.submitMission;

import com.hanghae.greenstep.shared.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubmitMissionResponseDto {
    private Long id;
    private Long userId;
    private String profilePhoto;
    private String email;
    private String missionName;
    private String missionType;
    private String missionImgUrl;
    private Status status;
    private String info;
    private String adminName;

    public SubmitMissionResponseDto(SubmitMission submitMission){
        this.id = submitMission.getId();
        this.userId = submitMission.getMember().getId();
        this.email = submitMission.getMember().getEmail();
        this.missionName = submitMission.getMission().getMissionName();
        this.missionImgUrl = submitMission.getImgUrl();
        this.status = submitMission.getStatus();
        this.info = submitMission.getInfo();
        this.adminName = submitMission.getAdminName();

    }
}
