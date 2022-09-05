package com.hanghae.greenstep.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements ApplicationRunner {

    @Autowired
    ApplicatonEventPublisher publisher;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        applicatonEventPublisher.publishEvent(new VerifyEvent(this, ))

    }

}
