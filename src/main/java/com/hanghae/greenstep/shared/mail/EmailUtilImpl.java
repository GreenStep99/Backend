package com.hanghae.greenstep.shared.mail;

import com.hanghae.greenstep.exception.CustomException;
import com.hanghae.greenstep.exception.ErrorCode;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@Component
public class EmailUtilImpl implements EmailUtil {
    private final JavaMailSender sender;

    public EmailUtilImpl(JavaMailSender sender) {
        this.sender = sender;
    }

    @Override
    public Map<String, Object> sendEmail(MailDto mailDto) throws MessagingException {
        Map<String, Object> result = new HashMap<>();
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        try {
            helper.setTo(mailDto.getToAddress());
            helper.setSubject(mailDto.getTitle());
            helper.setText(mailDto.getContent());
            result.put("resultCode", 200);
        } catch (MessagingException e) {
            e.printStackTrace();
            result.put("resultCode", 500);
        }

        sender.send(message);
        return result;
    }
}