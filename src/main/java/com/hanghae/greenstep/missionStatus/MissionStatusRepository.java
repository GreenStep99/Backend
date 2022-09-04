package com.hanghae.greenstep.missionStatus;

import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.mission.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionStatusRepository extends JpaRepository<MissionStatus,Long> {
    MissionStatus findByMemberAndMission(Member member, Mission mission);
}
