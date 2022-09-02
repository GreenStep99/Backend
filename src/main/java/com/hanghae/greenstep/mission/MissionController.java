package com.hanghae.greenstep.mission;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;

    @GetMapping("/missions/daily-lists")
    public ResponseEntity<?> getDailyMissions(HttpServletRequest request){
        return missionService.getDailyMissions(request);
    }

    @GetMapping("/missions/weekly-lists")
    public ResponseEntity<?> getWeeklyMissions(HttpServletRequest request){
        return missionService.getWeeklyMissions(request);
    }

    @GetMapping("/missions/today-lists")
    public ResponseEntity<?> getTodayMission(HttpServletRequest request){
        return missionService.getTodayMission(request);
    }

    @GetMapping("/missions/{missionId}")
    public ResponseEntity<?> getMissionDetail(@PathVariable Long missionId, HttpServletRequest request) throws Exception {
        return missionService.getMissionDetail(missionId, request);
    }

    @PostMapping("/missions/{missionId}")
    public ResponseEntity<?> submitMission(@PathVariable Long missionId, HttpServletRequest request, @RequestParam("photo") MultipartFile photo) throws Exception {
        return missionService.submitMission(missionId, request, photo);
    }
}