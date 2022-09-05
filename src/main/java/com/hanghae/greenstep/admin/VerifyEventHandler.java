package com.hanghae.greenstep.admin;

import com.hanghae.greenstep.mission.MissionService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class VerifyEventHandler {

    private MissionService missionService;

    @EventListener
    public void handle(VerifyEvent verifyEvent){
        missionService.changeStatus();
    }
}
