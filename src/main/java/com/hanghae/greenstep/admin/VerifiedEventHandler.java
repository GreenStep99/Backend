package com.hanghae.greenstep.admin;

import com.hanghae.greenstep.shared.mail.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

@Component
@Slf4j
@RequiredArgsConstructor
public class VerifiedEventHandler {

    private final EmailService emailService;

    @EventListener
    public void sendVerificationEmail(VerifiedEvent verifiedEvent) throws MessagingException {
        emailService.sendMail(verifiedEvent);
    }

}
