package com.hanghae.greenstep.shared.mail;

import com.hanghae.greenstep.admin.VerifiedEvent;
import com.hanghae.greenstep.shared.mail.Dto.MailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

import static com.hanghae.greenstep.shared.Status.DONE;
import static com.hanghae.greenstep.shared.Status.REJECTED;

@RequiredArgsConstructor
@Service
@Async
public class EmailService {
    private final EmailUtilImpl emailUtil;

    public void sendMail(VerifiedEvent verifiedEvent) throws MessagingException {
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
        emailUtil.sendEmail(mailDto);
    }
}
