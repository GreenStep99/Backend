package com.hanghae.greenstep.admin;

import com.hanghae.greenstep.shared.Status;
import com.hanghae.greenstep.submitMission.SubmitMission;
import org.springframework.stereotype.Component;

@Component
public class ApplicationEventPublisher {
    public void publishEvent(Status verification, SubmitMission submitMission, String info) {
    }

    public void publishEvent(VerifiedEvent verifiedEvent) {
    }
}
