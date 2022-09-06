package com.hanghae.greenstep.admin;

import com.hanghae.greenstep.feed.Feed;
import com.hanghae.greenstep.shared.mail.MailDto;
import com.hanghae.greenstep.submitMission.SubmitMission;

public class VerifyEvent {
    private String verification;

    private Feed feed;

    private SubmitMission submitMission;

    private MailDto mailDto;

    public VerifyEvent(Object source) {
        super(source);
    }

    public VerifyEvent(Object source, String verification) {
        super(source);
        this.verification = verification;
    }

    public String getVerification() {
        return verification;
    }

}
