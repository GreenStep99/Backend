package com.hanghae.greenstep.admin;

public class VerifyEvent {
    private String verification;

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
