package com.hanghae.greenstep.shared;

import com.hanghae.greenstep.submitMission.SubmitMission;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class AccountEntityListner {
    @PrePersist
    @PreUpdate
    public void prePersistAndPreUpdate(Object o){
        MissionHistoryRepository accountHistoryRepository =BeanUtil.getBean(MissionHistoryRepository.class);

        SubmitMission missions= (SubmitMission) o;

        MissionHistory accountHistory = MissionHistory.builder()
                .memberId(missions.getMember().getId())
                .missionImageUrl(missions.getImgUrl())
                .missionName(missions.getMission().getMissionName())
                .missionId(missions.getMission().getId())
                .info(missions.getReason())
                .status(missions.getStatus())
                .adminName(missions.getAdminName())
                .build();

    }
}
