package com.hanghae.greenstep.shared.mail;

import java.util.Map;

public interface EmailUtil {
    Map<String, Object> sendEmail(MailDto mailDto);
}