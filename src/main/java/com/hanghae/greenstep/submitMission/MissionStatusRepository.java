package com.hanghae.greenstep.submitMission;

import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.mission.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface MissionStatusRepository extends JpaRepository<MissionStatus,Long> {

    Optional<MissionStatus> findByMemberAndMission(Member member, Mission mission);

    @Transactional
    @Modifying
    @Query(value = "delete from MissionStatus m where m.missionType = 'daily'")
    void deleteAllDailyMissionStatus();
    @Transactional
    @Modifying
    @Query(value = "delete from MissionStatus m where m.missionType = 'weekly'")
    void deleteAllWeeklyMissionStatus();
    @Transactional
    @Modifying
    @Query(value = "delete from MissionStatus m where m.missionType = 'challenge'")
    void deleteAllChallengeMissionStatus();
}
