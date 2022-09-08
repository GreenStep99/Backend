package com.hanghae.greenstep.admin;

import org.springframework.stereotype.Component;

@Component
public class ApplicationEventPublisher {

    public void publishEvent(VerifiedEvent verifiedEvent) {
        System.out.println("***********************************************돈다******************");

    }
}
