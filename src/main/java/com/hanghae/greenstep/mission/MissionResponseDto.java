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

    private String missionName;

    private String missionContent;

    private String missionImageUrl;


    private String missionType;


    private Boolean onShow;
}
