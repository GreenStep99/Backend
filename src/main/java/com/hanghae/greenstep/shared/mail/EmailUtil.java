package com.hanghae.greenstep.shared.mail;

import javax.mail.MessagingException;

public interface EmailUtil {
    void sendEmail(MailDto mailDto) throws MessagingException;
}