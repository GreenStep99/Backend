package com.hanghae.greenstep.submitMission;

import com.hanghae.greenstep.mission.Dto.MissionRequestDto;
import com.hanghae.greenstep.shared.Message;
import com.hanghae.greenstep.submitMission.Dto.MyMissionsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SubmitMissionController {

    private final SubmitMissionService submitMissionService;

    @GetMapping("/profiles/missions/waiting-status")
    public ResponseEntity<?> getWaitingMissions(HttpServletRequest request){
        List<MyMissionsDto> myMissionsDtoList = submitMissionService.getWaitingMissions(request);
        return new ResponseEntity<>(Message.success(myMissionsDtoList), HttpStatus.OK);
    }

    @GetMapping("/profiles/missions")
    public ResponseEntity<?> getMyMissions(HttpServletRequest request){
        List<MyMissionsDto> myMissionsDtoList = submitMissionService.getMyMissions(request);
        return new ResponseEntity<>(Message.success(myMissionsDtoList), HttpStatus.OK);
    }

    @GetMapping("/profiles/setting/hidden-missions")
    public ResponseEntity<?> getHiddenMissions(HttpServletRequest request){
        List<MyMissionsDto> myMissionsDtoList = submitMissionService.getHiddenMissions(request);
        return new ResponseEntity<>(Message.success(myMissionsDtoList), HttpStatus.OK);
    }

    @PatchMapping("/profiles/missions")
<<<<<<< HEAD
    public ResponseEntity<?> hideMyMissions( @RequestBody Long[] missionsIdList, HttpServletRequest request){
        for(Long id : missionsIdList){
            System.out.println(id);
        }
=======
    public ResponseEntity<?> hideMyMissions(@RequestBody Long[] missionsIdList, HttpServletRequest request){
>>>>>>> 2a07156210083523b7118cf4252831d090cfba90
        List<MyMissionsDto> myMissionsDtoList = submitMissionService.hideMyMissions(missionsIdList, request);
        return new ResponseEntity<>(Message.success(myMissionsDtoList), HttpStatus.OK);
    }

    @PostMapping("/missions/{missionId}")
    public ResponseEntity<?> submitMission(@PathVariable Long missionId, HttpServletRequest request, @RequestBody MissionRequestDto missionRequestDto) throws Exception {
        submitMissionService.submitMission(missionId, request, missionRequestDto);
        return new ResponseEntity<>(Message.success("전송 완료"),HttpStatus.OK);
    }

}
