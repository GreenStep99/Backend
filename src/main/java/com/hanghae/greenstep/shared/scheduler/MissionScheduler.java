package com.hanghae.greenstep.shared.scheduler;

import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.member.MemberRepository;
import com.hanghae.greenstep.mission.MissionRepository;
import com.hanghae.greenstep.submitMission.MissionStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class MissionScheduler {

    private final MissionRepository missionRepository;

    private final MissionStatusRepository missionStatusRepository;

    private final MemberRepository memberRepository;


    @Scheduled(cron = "0 0 0 1/1 * ?")
//    @Scheduled(cron = "0 0/1 * 1/1 * ?")
    @Transactional
    public void resetAndUpdateDailyMission() {
        log.info("Scheduled Run");
        List<Member> allMember = memberRepository.findAll();
        for (Member member : allMember) {
            member.resetDailyPoint();
        }
        missionStatusRepository.deleteAllDailyMissionStatus();
//        List<Mission> allDailyMissionList = missionRepository.findAllDailyMission();
//        for (Mission mission : allDailyMissionList) {
//            mission.updateOnShow(false);
//        }
//        List<Mission> dailyMissionList = missionRepository.findDailyMission();
//        for (Mission mission : dailyMissionList) {
//            log.info(mission.getMissionName());
//            mission.updateOnShow(true);
//        }
    }
    
    @Scheduled(cron = "0 0 0 ? * SUN")
    @Transactional
    public void resetAndUpdateWeeklyMission() {
        log.info("Scheduled Run");
        missionStatusRepository.deleteAllWeeklyMissionStatus();
//        List<Mission> allDailyMissionList = missionRepository.findAllWeeklyMission();
//        for (Mission mission : allDailyMissionList) {
//            mission.updateOnShow(false);
//        }
//        List<Mission> dailyMissionList = missionRepository.findWeeklyMission();
//        for (Mission mission : dailyMissionList) {
//            log.info(mission.getMissionName());
//            mission.updateOnShow(true);
//        }
    }

    @Scheduled(cron = "0 0 0 1/1 * ?")
    @Transactional
    public void resetAndUpdateChallengeMission() {
        log.info("Scheduled Run");
        missionStatusRepository.deleteAllChallengeMissionStatus();
//        List<Mission> allDailyMissionList = missionRepository.findAllChallengeMission();
//        for (Mission mission : allDailyMissionList) {
//            mission.updateOnShow(false);
//        }
//        List<Mission> dailyMissionList = missionRepository.findChallengeMission();
//        for (Mission mission : dailyMissionList) {
//            log.info(mission.getMissionName());
//            mission.updateOnShow(true);
//        }
    }

}
