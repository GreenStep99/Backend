package com.hanghae.greenstep.rank;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RankController {
    private final RankService rankService;
    @GetMapping("/ranks/missions")
    public ResponseEntity<?> getRankMissions(){
        return rankService.getRankMissions();
    }

    @GetMapping("/rank/mission/point")
    public ResponseEntity<?> getRankMissionPoint() {
        return rankService.getRankMissionPoint();
    }
    @GetMapping("/rank/mission/point/daily")
    public ResponseEntity<?> getDailyRankMissionPoint() {
        return rankService.getDailyRankMissionPoint();
    }
    @GetMapping("/rank/mission/point/weekly")
    public ResponseEntity<?> getWeeklyRankMissionPoint() {
        return rankService.getWeeklyRankMissionPoint();
    }
}
