package com.hanghae.greenstep.admin;


import com.hanghae.greenstep.exception.CustomException;
import com.hanghae.greenstep.exception.ErrorCode;
import com.hanghae.greenstep.jwt.TokenProvider;
import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.member.MemberRepository;
import com.hanghae.greenstep.missionStatus.MissionStatus;
import com.hanghae.greenstep.missionStatus.MissionStatusRepository;
import com.hanghae.greenstep.shared.Check;
import com.hanghae.greenstep.shared.Message;
import com.hanghae.greenstep.shared.Status;
import com.hanghae.greenstep.shared.mail.EmailUtil;
import com.hanghae.greenstep.shared.mail.EmailUtilImpl;
import com.hanghae.greenstep.shared.mail.MailDto;
import com.hanghae.greenstep.submitMission.SubmitMission;
import com.hanghae.greenstep.submitMission.SubmitMissionRepository;
import com.hanghae.greenstep.submitMission.SubmitMissionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.hanghae.greenstep.shared.Status.DONE;
import static com.hanghae.greenstep.shared.Status.REJECTED;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final SubmitMissionRepository submitMissionRepository;

    private final MissionStatusRepository missionStatusRepository;

    private final Check check;
    private final EmailUtilImpl emailUtil;

    public ResponseEntity<?> getSubmitMission() {
        List<SubmitMission> submitMissionList = submitMissionRepository.findAllByOrderByCreatedAtAsc();
        List<SubmitMissionResponseDto> submitMissionResponseDtoList = new ArrayList<>();
        for (SubmitMission submitMission : submitMissionList) {
            submitMissionResponseDtoList.add(
                    SubmitMissionResponseDto.builder()
                            .id(submitMission.getId())
                            .userId(submitMission.getMember().getId())
                            .profilePhoto(submitMission.getMember().getProfilePhoto())
                            .email(submitMission.getMember().getEmail())
                            .missionName(submitMission.getMissionName())
                            .missionType(submitMission.getMissionType())
                            .missionImgUrl(submitMission.getImgUrl())
                            .adminName(submitMission.getAdminName())
                            .info(submitMission.getInfo())
                            .status(submitMission.getStatus())
                            .build()
            );
        }
        return new ResponseEntity<>(Message.success(submitMissionResponseDtoList), HttpStatus.OK);
    }

    public ResponseEntity<?> login(AdminLoginRequestDto adminLoginRequestDto, HttpServletResponse response) {
        Member admin = memberRepository.findByEmail(adminLoginRequestDto.getEmail()).orElseThrow(
        );
        AdminTokenDto tokenDto = tokenProvider.generateTokenDto(admin);
        tokenToHeaders(tokenDto, response);
        AdminLoginResponseDto adminLoginResponseDto = new AdminLoginResponseDto(admin.getId(), admin.getName());
        return new ResponseEntity<>(Message.success(adminLoginResponseDto), HttpStatus.OK);
    }

    public void tokenToHeaders(AdminTokenDto tokenDto, HttpServletResponse response) {
        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.addHeader("Refresh_Token", tokenDto.getRefreshToken());
        response.addHeader("Access_Token_Expire_Time", tokenDto.getAccessTokenExpiresIn().toString());
    }

    @Transactional
    public ResponseEntity<?> verifySubmitMission(Status verification, Long submitMissionId, HttpServletRequest request, String info) {
        Member admin = check.accessTokenCheck(request);
        SubmitMission submitMission = submitMissionRepository.findById(submitMissionId).orElseThrow();
        if(submitMission.getMember().getAcceptMail()) sendMail(verification,submitMission,info);
        changeMissionStatus(verification, submitMission, admin, info);
        SubmitMissionResponseDto submitMissionResponseDto = new SubmitMissionResponseDto(submitMission);
        return new ResponseEntity<>(Message.success(submitMissionResponseDto), HttpStatus.OK);
    }

    public void changeMissionStatus(Status verification, SubmitMission submitMission, Member admin, String info) {
        submitMission.update(verification, info, admin.getName());
        MissionStatus missionStatus = missionStatusRepository.findByMemberAndMission(submitMission.getMember(), submitMission.getMission())
                .orElseThrow(() -> new CustomException(ErrorCode.MISSION_STATUS_NOT_FOUND));
        missionStatus.update(verification);
        if (verification == DONE) {
            if (Objects.equals(submitMission.getMissionType(), "daily")) {
                submitMission.getMember().earnDailyPoint();
            } else if (Objects.equals(submitMission.getMissionType(), "weekly")) {
                submitMission.getMember().earnWeeklyPoint();
            } else {
                submitMission.getMember().earnChallengePoint();
            }
        }
    }

    public void sendMail(Status verification, SubmitMission submitMission, String info){
        String title ="[GreenStep] 미션 인증이 ";
        String content = "인증하신 ";
        if(verification==DONE) {title +="완료되었습니다.";
        content += "[" + submitMission.getMission().getMissionName() + "]가(이) 성공적으로 인증되었습니다!";}
        if(verification==REJECTED) {title +="실패하였습니다.";
            content += "<html lang=\"en\" dir=\"ltr\">     //html 파일임을 정의. html 태그는 head 태그와 body 태그로 이루어짐.\n" +
                    "  <head>                       //메타 데이터의 집합으로 웹 페이지에 직접적으로 보이지 않는 정보임. head 태그는 title, meta, link, style, script 태그로 구성됨.\n" +
                    "    <meta charset=\"utf-8\">     //meta 태그는 <meta 속성=\"속성값\" />으로 스스토 닫는 태그이기 때문에 이렇게 나타낸다. 보기에 charset=\"utf-8\"는 글자 깨짐에 대해 다른 언어에서도 볼수 있도록 표기한 것이다.\n" +
                    "    <title>[" + submitMission.getMission().getMissionName() + "]가(이) 인증에 실패하였습니다.\n 인증 실패 사유: " + info + "\n 다시 인증해주세요!"+"</title>            //title 태그는 페이지를 대표하는 내용의 제목을 넣어야 함.\n" +
                    "  </head>\n" +
                    "  <body>                       //본문의 내용을 담는 공간이다.\n" +
                    " \n" +
                    "  </body>\n" +
                    "</html>";}
        MailDto mailDto = new MailDto(submitMission.getMember().getEmail(),title, content);

    }
}
