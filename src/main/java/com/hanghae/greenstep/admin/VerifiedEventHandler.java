package com.hanghae.greenstep.admin;

import com.hanghae.greenstep.shared.mail.EmailUtilImpl;
import com.hanghae.greenstep.shared.mail.MailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.Map;

import static com.hanghae.greenstep.shared.Status.DONE;
import static com.hanghae.greenstep.shared.Status.REJECTED;

@Component
@Slf4j
@RequiredArgsConstructor
public class VerifiedEventHandler {

    private final EmailUtilImpl emailUtil;

    @EventListener
    public Map<String, Object> sendMail(VerifiedEvent verifiedEvent) throws MessagingException {
        System.out.println("88888888888888888메일 전송 중888888888888888888888888");
        String title = "[GreenStep] 미션 인증이 ";
        String content = "인증하신 ";
        if (verifiedEvent.getVerification() == DONE) {
            title += "완료되었습니다.";
            content += "[" + verifiedEvent.getSubmitMission().getMission().getMissionName() +
                    "]가(이) 성공적으로 인증되었습니다!\n" + "인증 날짜 :" +
                    verifiedEvent.getSubmitMission().getCreatedAt().toLocalDate();
        }
        if (verifiedEvent.getVerification() == REJECTED) {
            title += "실패하였습니다.";
            content += "[" + verifiedEvent.getSubmitMission().getMission().getMissionName() +
                    "]가(이) 인증에 실패하였습니다.\n 인증 실패 사유: " + verifiedEvent.getInfo() + "\n 다시 인증해주세요!" +
                    "인증 날짜 :" + verifiedEvent.getSubmitMission().getCreatedAt().toLocalDate();
        }
        MailDto mailDto = new MailDto(verifiedEvent.getSubmitMission().getMember().getEmail(), title, content);
        return emailUtil.sendEmail(mailDto);
    }

}
