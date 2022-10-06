package com.hanghae.greenstep.mission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MissionRepository extends JpaRepository<Mission,Long> {

    @Query(value = "select m from Mission m where m.onShow = true AND m.missionType = 'daily'")
    List<Mission> findDailyMissionByOnShow();

    @Query(value = "select m from Mission m where m.onShow = true AND m.missionType = 'weekly'")
    List<Mission> findWeeklyMissionByOnShow();

    @Query(value = "select m from Mission m where m.onShow = true AND m.missionType = 'challenge'")
    List<Mission> findTodayMissionByOnShow();

    @Query(value = "select * from mission where mission_type = 'daily'", nativeQuery = true)
    List<Mission> findAllDailyMission();
    @Query(value = "select * from mission where mission_type = 'daily' AND mission_icon_url is not null order by RAND() limit 8", nativeQuery = true)
    List<Mission> findDailyMission();

    @Query(value = "select * from mission where mission_type = 'weekly'", nativeQuery = true)
    List<Mission> findAllWeeklyMission();
    @Query(value = "select * from mission where mission_type = 'weekly' AND mission_icon_url is not null order by RAND() limit 4", nativeQuery = true)
    List<Mission> findWeeklyMission();

    @Query(value = "select * from mission where mission_type = 'challenge'", nativeQuery = true)
    List<Mission> findAllChallengeMission();
    @Query(value = "select * from mission where mission_type = 'challenge' AND mission_icon_url is not null order by RAND() limit 1", nativeQuery = true)
    List<Mission> findChallengeMission();
}
