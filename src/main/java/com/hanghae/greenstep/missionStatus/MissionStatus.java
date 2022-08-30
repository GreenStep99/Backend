package com.hanghae.greenstep.missionStatus;

import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.mission.Mission;
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
    private Boolean missionStatus;
}
