package com.hanghae.greenstep.mission;

import com.hanghae.greenstep.shared.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;

    @GetMapping("/missions/daily-lists")
    public ResponseEntity<?> getDailyMissions(HttpServletRequest request){
        List<MissionResponseDto> missionResponseDtoList = missionService.getDailyMissions(request);
        return new ResponseEntity<>(Message.success(missionResponseDtoList), HttpStatus.OK);
    }

    @GetMapping("/missions/weekly-lists")
    public ResponseEntity<?> getWeeklyMissions(HttpServletRequest request){
        List<MissionResponseDto> missionResponseDtoList = missionService.getWeeklyMissions(request);
        return new ResponseEntity<>(Message.success(missionResponseDtoList), HttpStatus.OK);
    }

    @GetMapping("/missions/today-lists")
    public ResponseEntity<?> getTodayMission(HttpServletRequest request){
        List<MissionResponseDto> missionResponseDtoList = missionService.getTodayMission(request);
        return new ResponseEntity<>(Message.success(missionResponseDtoList), HttpStatus.OK);
    }

//    @GetMapping("/missions/{missionId}")
//    public ResponseEntity<?> getMissionDetail(@PathVariable Long missionId, HttpServletRequest request) {
//        return missionService.getMissionDetail(missionId, request);
//    }

}
