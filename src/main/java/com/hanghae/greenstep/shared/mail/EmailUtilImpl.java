package com.hanghae.greenstep.shared.mail;

import com.hanghae.greenstep.shared.mail.Dto.MailDto;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

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
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        sender.send(message);
    }
}