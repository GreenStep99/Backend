package com.hanghae.greenstep.admin;


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
    public ResponseEntity<?> verifySubmitMission(Status verification,Long submitMissionId, HttpServletRequest request, String info) {
        Member admin = check.accessTokenCheck(request);
        SubmitMission submitMission = submitMissionRepository.findById(submitMissionId).orElseThrow();
        verifyMission(verification,submitMission,admin, info);
        sendMail(verification,submitMission,info);
        SubmitMissionResponseDto submitMissionResponseDto = new SubmitMissionResponseDto(submitMission);
        return new ResponseEntity<>(Message.success(submitMissionResponseDto),HttpStatus.OK);
    }

    public void verifyMission(Status verification, SubmitMission submitMission, Member admin, String info){
           submitMission.update(verification, info, admin.getName());
            MissionStatus missionStatus = missionStatusRepository.findByMemberAndMission(submitMission.getMember(),submitMission.getMission());
            missionStatus.update(verification);
            if(verification == DONE){
                if(Objects.equals(submitMission.getMissionType(), "dailyMission")){
                    submitMission.getMember().earnDailyPoint();
                }else if(Objects.equals(submitMission.getMissionType(), "weeklyMission")){
                    submitMission.getMember().earnWeeklyPoint();
                }else{
                    submitMission.getMember().earnChallengePoint();
                }
            }
    }

    public void sendMail(Status verification, SubmitMission submitMission, String info){
        String title ="[GreenStep] 미션 인증이 ";
        String content = "인증하신 ";
        if(verification==DONE) {title +="완료되었습니다.";
        content += submitMission.getMission().getMissionName()+"이 성공적으로 인증되었습니다!";}
        if(verification==REJECTED) {title +="실패하였습니다.";
            content += submitMission.getMission().getMissionName()+"이 인증에 실패하였습니다.\n 인증 실패 사유: "+ info +"\n 다시 인증해주세요!";}
        MailDto mailDto = new MailDto(submitMission.getMember().getEmail(),title, content);
        emailUtil.sendEmail(mailDto);
    }

}
