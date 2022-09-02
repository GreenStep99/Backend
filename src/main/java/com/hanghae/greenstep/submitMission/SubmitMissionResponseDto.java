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
    private Long userId;
    private String profilePhoto;
    private String nickname;
    private String missionName;
    private String missionImgUrl;
    private Status status;
}
