package com.hanghae.greenstep.mission;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MissionResponseDto {

    private Long missionId;

    private String missionContent;

    private String missionImageUrl;


    private String missionType;

    private String missionName;

    private Boolean onShow;
}
