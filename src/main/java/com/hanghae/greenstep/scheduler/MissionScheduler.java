package com.hanghae.greenstep.scheduler;

import com.hanghae.greenstep.mission.MissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MissionScheduler {

    private final MissionRepository missionRepository;

//    @Scheduled(cron = "0/10 * * * * ?")
//    @Transactional
//    public void resetAndUpdateDailyMission() {
//        log.info("Scheduled Run");
//        List<Mission> allDailyMissionList = missionRepository.findAllDailyMission();
//        for(Mission mission : allDailyMissionList){
//            mission.updateOnShow(false);
//        }
//        List<Mission> dailyMissionList = missionRepository.findDailyMission();
//        for(Mission mission : dailyMissionList){
//            log.info(mission.getMissionName());
//            mission.updateOnShow(true);
//        }
//    }
//    @Scheduled(cron = "0/10 * * * * ?")
//    @Transactional
//    public void resetAndUpdateWeeklyMission() {
//        log.info("Scheduled Run");
//        List<Mission> allDailyMissionList = missionRepository.findAllWeeklyMission();
//        for(Mission mission : allDailyMissionList){
//            mission.updateOnShow(false);
//        }
//        List<Mission> dailyMissionList = missionRepository.findWeeklyMission();
//        for(Mission mission : dailyMissionList){
//            log.info(mission.getMissionName());
//            mission.updateOnShow(true);
//        }
//    }
//    @Scheduled(cron = "0/10 * * * * ?")
//    @Transactional
//    public void resetAndUpdateChallengeMission() {
//        log.info("Scheduled Run");
//        List<Mission> allDailyMissionList = missionRepository.findAllChallengeMission();
//        for(Mission mission : allDailyMissionList){
//            mission.updateOnShow(false);
//        }
//        List<Mission> dailyMissionList = missionRepository.findChallengeMission();
//        for(Mission mission : dailyMissionList){
//            log.info(mission.getMissionName());
//            mission.updateOnShow(true);
//        }
//    }
}
