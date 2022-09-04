package com.hanghae.greenstep.submitMission;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class SubmitMissionController {

    private final SubmitMissionService submitMissionService;

    @GetMapping("/profiles/missions")
    public ResponseEntity<?> getMyMissions(HttpServletRequest request){
        return submitMissionService.getMyMissions(request);
    }
}
