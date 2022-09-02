package com.hanghae.greenstep.missionStatus;

import com.hanghae.greenstep.shared.Status;
import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.mission.Mission;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Column
    private Status missionStatus;

    @Builder
    public MissionStatus(Member member, Mission mission, Status missionStatus) {
        this.member = member;
        this.mission = mission;
        this.missionStatus = missionStatus;
    }
}
