package com.hanghae.greenstep.admin;

import com.hanghae.greenstep.shared.Status;
import com.hanghae.greenstep.submitMission.SubmitMission;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VerifiedEvent {
    private Status verification;
    private SubmitMission submitMission;
    private String info;
}
