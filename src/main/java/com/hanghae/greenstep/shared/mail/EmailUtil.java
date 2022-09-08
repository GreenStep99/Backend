package com.hanghae.greenstep.shared.mail;

import javax.mail.MessagingException;
import java.util.Map;

public interface EmailUtil {
    Map<String, Object> sendEmail(MailDto mailDto) throws MessagingException;
}