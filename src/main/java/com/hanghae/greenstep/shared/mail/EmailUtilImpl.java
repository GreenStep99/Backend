package com.hanghae.greenstep.shared.mail;

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
    public void sendEmail(MailDto mailDto) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        try {
            helper.setTo(mailDto.getToAddress());
            helper.setSubject(mailDto.getTitle());
            helper.setText(mailDto.getContent(),true);
            System.out.println("88888888888888888메일 전송 성공888888888888888888888888");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("88888888888888888메일 전송 실패888888888888888888888888");
        }
        sender.send(message);
    }
}