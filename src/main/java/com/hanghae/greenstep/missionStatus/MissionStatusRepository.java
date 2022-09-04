package com.hanghae.greenstep.missionStatus;

import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.mission.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MissionStatusRepository extends JpaRepository<MissionStatus,Long> {
    MissionStatus findByMemberAndMission(Member member, Mission mission);

    @Query(value = "delete from MissionStatus m where m.missionType = 'daily'")
    void deleteAllDailyMissionStatus();

    @Query(value = "delete from MissionStatus m where m.missionType = 'weekly'")
    void deleteAllWeeklyMissionStatus();

    @Query(value = "delete from MissionStatus m where m.missionType = 'challenge'")
    void deleteAllChallengeMissionStatus();
}
