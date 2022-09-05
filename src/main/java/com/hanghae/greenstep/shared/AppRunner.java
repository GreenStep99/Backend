package com.hanghae.greenstep.shared;

import com.hanghae.greenstep.admin.VerifyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements ApplicationRunner {

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        applicationEventPublisher.publishEvent(new VerifyEvent(this, "OK"));
        applicationEventPublisher.publishEvent(new VerifyEvent(this, "NO"));
    }
}
