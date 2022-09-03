package com.hanghae.greenstep.shared;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class MissionHistory extends Timestamped {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Long missionId;
    @Column
    private String missionName;
    @Column
    private String missionImageUrl;
    @Column
    private Long memberId;
    @Column
    private Status status;
    @Column
    private String info;

    @Column
    private String adminName;

    @Builder
    public MissionHistory(Long missionId, String missionName, String missionImageUrl, Long memberId, Status status, String info, String adminName) {
        this.id = getId();
        this.missionId = missionId;
        this.missionName = missionName;
        this.missionImageUrl = missionImageUrl;
        this.memberId = memberId;
        this.status = status;
        this.info = info;
        this.adminName = adminName;
    }
}
