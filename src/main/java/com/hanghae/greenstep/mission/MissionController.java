package com.hanghae.greenstep.mission;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;

    @GetMapping("/missions")
    public ResponseEntity<?> getMissions(){
        return missionService.getMissions();
    }
}
