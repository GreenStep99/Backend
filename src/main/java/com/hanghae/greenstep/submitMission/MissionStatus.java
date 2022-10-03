package com.hanghae.greenstep.submitMission;

import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.mission.Mission;
import com.hanghae.greenstep.shared.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.hanghae.greenstep.shared.Status.REJECTED;

@Entity
@NoArgsConstructor
@Getter
public class MissionStatus {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Mission mission;

    @Enumerated(EnumType.STRING)
    private Status missionStatus;

    @Column(length = 80)
    private String missionType;

    @Builder
    public MissionStatus(Member member, Mission mission, Status missionStatus, String missionType) {
        this.member = member;
        this.mission = mission;
        this.missionStatus = missionStatus;
        this.missionType = missionType;
    }

    public void update(Status verification) {
        this.missionStatus = verification;
    }
}
