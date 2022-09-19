package com.hanghae.greenstep.shared.mail;

import com.hanghae.greenstep.shared.mail.Dto.MailDto;

import javax.mail.MessagingException;

public interface EmailUtil {
    void sendEmail(MailDto mailDto) throws MessagingException;
}