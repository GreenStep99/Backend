package com.hanghae.greenstep.submitMission;

import com.hanghae.greenstep.mission.MissionRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RequiredArgsConstructor
public class SubmitMissionController {

    private final SubmitMissionService submitMissionService;

    @GetMapping("/profiles/missions")
    public ResponseEntity<?> getMyMissions(HttpServletRequest request){
        return submitMissionService.getMyMissions(request);
    }

    @GetMapping("/profiles/setting/hidden-missions")
    public ResponseEntity<?> getHiddenMissions(HttpServletRequest request){
        return submitMissionService.getHiddenMissions(request);
    }

    @PatchMapping("/profiles/missions")
    public ResponseEntity<?> hideMyMissions( @RequestBody Long[] missionsIdList, HttpServletRequest request){
        return submitMissionService.hideMyMissions(missionsIdList, request);
    }

    @PostMapping("/missions/{missionId}")
    public ResponseEntity<?> submitMission(@PathVariable Long missionId, HttpServletRequest request, @RequestBody MissionRequestDto missionRequestDto) throws Exception {
        return submitMissionService.submitMission(missionId, request, missionRequestDto);
    }

}
