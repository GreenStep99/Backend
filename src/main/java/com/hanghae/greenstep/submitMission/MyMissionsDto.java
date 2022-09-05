package com.hanghae.greenstep.submitMission;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyMissionsDto {

    private Long id;
    private String missionName;
    private String missionImgUrl;
    private Boolean onFeed;

    private String tag;


    public MyMissionsDto(SubmitMission submitMission){
        this.id = submitMission.getId();
        this.missionName = submitMission.getMission().getMissionName();
        this.missionImgUrl = submitMission.getImgUrl();
        this.onFeed = submitMission.getOnFeed();
        this.tag = submitMission.getMission().getTag();
    }
}
