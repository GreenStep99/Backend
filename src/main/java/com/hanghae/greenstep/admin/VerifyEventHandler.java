package com.hanghae.greenstep.admin;

import com.hanghae.greenstep.shared.mail.EmailUtilImpl;
import com.hanghae.greenstep.shared.mail.MailDto;
import com.hanghae.greenstep.submitMission.SubmitMission;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VerifyEventHandler {

    private final EmailUtilImpl emailUtil;

    @EventListener
    public void handle(VerifyEvent verifyEvent, SubmitMission submitMission){
        String verification = verifyEvent.getVerification();
        MailDto mailDto = new MailDto(submitMission.getMember().getEmail(), verification, submitMission.getMission().getMissionName());
        emailUtil.sendEmail(mailDto);
    }
}
