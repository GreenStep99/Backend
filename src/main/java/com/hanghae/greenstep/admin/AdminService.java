package com.hanghae.greenstep.admin;


import com.hanghae.greenstep.admin.Dto.AdminLoginRequestDto;
import com.hanghae.greenstep.admin.Dto.AdminLoginResponseDto;
import com.hanghae.greenstep.admin.Dto.AdminTokenDto;
import com.hanghae.greenstep.exception.CustomException;
import com.hanghae.greenstep.exception.ErrorCode;
import com.hanghae.greenstep.jwt.TokenProvider;
import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.member.MemberRepository;
import com.hanghae.greenstep.notice.NotificationService;
import com.hanghae.greenstep.notice.NotificationType;
import com.hanghae.greenstep.shared.Authority;
import com.hanghae.greenstep.shared.Check;
import com.hanghae.greenstep.shared.Status;
import com.hanghae.greenstep.submitMission.Dto.SubmitMissionResponseDto;
import com.hanghae.greenstep.submitMission.Dto.VerificationInfoDto;
import com.hanghae.greenstep.submitMission.MissionStatus;
import com.hanghae.greenstep.submitMission.MissionStatusRepository;
import com.hanghae.greenstep.submitMission.SubmitMission;
import com.hanghae.greenstep.submitMission.SubmitMissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.hanghae.greenstep.shared.Status.DONE;


@Service
@RequiredArgsConstructor
public class AdminService {
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final SubmitMissionRepository submitMissionRepository;
    private final MissionStatusRepository missionStatusRepository;
    private final Check check;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher publisher;
    private final NotificationService notificationService;

    @Transactional(readOnly=true)
    public List<SubmitMissionResponseDto> getSubmitMission(HttpServletRequest request) {
        Member admin = check.accessTokenCheck(request);
        check.checkAdmin(admin);
        List<SubmitMission> submitMissionList = submitMissionRepository.findAllByOrderByCreatedAtAscFetchJoin();
        List<SubmitMissionResponseDto> submitMissionResponseDtoList = new ArrayList<>();
        for (SubmitMission submitMission : submitMissionList) {
            submitMissionResponseDtoList.add(
                    SubmitMissionResponseDto.builder()
                            .id(submitMission.getId())
                            .userId(submitMission.getMember().getId())
                            .profilePhoto(submitMission.getMember().getProfilePhoto())
                            .email(submitMission.getMember().getEmail())
                            .missionName(submitMission.getMission().getMissionName())
                            .missionType(submitMission.getMission().getMissionType())
                            .missionImgUrl(submitMission.getImgUrl())
                            .adminName(submitMission.getAdminName())
                            .info(submitMission.getInfo())
                            .status(submitMission.getStatus())
                            .build()
            );
        }
        return submitMissionResponseDtoList;
    }

    //n+1 문제 없음
    public AdminLoginResponseDto login(AdminLoginRequestDto adminLoginRequestDto, HttpServletResponse response) {
        blockSqlSentence(adminLoginRequestDto);
        Member admin = memberRepository.findByEmailAndRole(adminLoginRequestDto.getEmail(), Authority.ROLE_ADMIN).orElseThrow(
                () -> new CustomException(ErrorCode.MEMBER_NOT_ALLOWED));
        if (!admin.validatePassword(passwordEncoder, adminLoginRequestDto.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_MEMBER_INFO);
        }
        AdminTokenDto tokenDto = tokenProvider.generateTokenDto(admin);
        tokenToHeaders(tokenDto, response);
        return new AdminLoginResponseDto(admin.getId(), admin.getName());
    }

    public void tokenToHeaders(AdminTokenDto tokenDto, HttpServletResponse response) {
        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.addHeader("Access_Token_Expire_Time", tokenDto.getAccessTokenExpiresIn().toString());
    }

    //n:1
    @Transactional
    public SubmitMissionResponseDto verifySubmitMission(Status verification, Long submitMissionId, HttpServletRequest request, VerificationInfoDto verificationInfoDto) {
        Member admin = check.accessTokenCheck(request);
        check.checkAdmin(admin);
        SubmitMission submitMission = submitMissionRepository.findByIdFetchJoin(submitMissionId).orElseThrow(
                        () -> new CustomException(ErrorCode.MISSION_NOT_FOUND)
                );
        String info = "";
        if(verificationInfoDto != null) info = verificationInfoDto.getInfo();
        if(Boolean.TRUE.equals(submitMission.getMember().getAcceptMail())) publisher.publishEvent(new VerifiedEvent(verification,submitMission,info));
        changeMissionStatus(verification, submitMission, admin, info);
        if(verification==DONE)earnMissionPoints(submitMission);

        if(verification == Status.REJECTED){
            //홈으로 이동하는 url
            String Url = "/mission";
            //댓글 생성 시 모집글 작성 유저에게 실시간 알림 전송 ,
            String content = "["+submitMission.getMission().getMissionName()+"]미션 인증이 거부되었습니다. 다시 시도해 주세요!";
            String imgUrl = "nullImg";
            notificationService.send(submitMission.getMember(), NotificationType.APPROVE, content, Url, imgUrl);

            return new SubmitMissionResponseDto(submitMission);
        }

        //마이페이지로 이동하는 url
        String Url = "/mypage";
        //댓글 생성 시 모집글 작성 유저에게 실시간 알림 전송 ,
        String content = "["+submitMission.getMission().getMissionName()+"]미션 인증이 완료되었습니다. 지금 바로 피드에 공유해보세요!";
        String imgUrl = "nullImg";
        notificationService.send(submitMission.getMember(), NotificationType.APPROVE, content, Url, imgUrl);

        return new SubmitMissionResponseDto(submitMission);
    }

    //n+1 문제 없음
    public void changeMissionStatus(Status verification, SubmitMission submitMission, Member admin,@Nullable String info) {
        submitMission.update(verification, info, admin.getName());
        Optional<MissionStatus> missionStatus = missionStatusRepository.findByMemberAndMission(submitMission.getMember(), submitMission.getMission());
        missionStatus.ifPresent(status -> status.update(verification));
    }

    public void earnMissionPoints(SubmitMission submitMission) {
        if (Objects.equals(submitMission.getMission().getMissionType(), "daily"))
            submitMission.getMember().earnTenPoint();
        if (Objects.equals(submitMission.getMission().getMissionType(), "weekly"))
            submitMission.getMember().earnTwentyPoint();
        if (Objects.equals(submitMission.getMission().getMissionType(), "challenge"))
            submitMission.getMember().earnThirtyPoint();
    }

    public void blockSqlSentence(AdminLoginRequestDto requestDto) {
        if (!requestDto.getEmail().matches("[a-zA-Z\\d]{3,15}@[a-zA-Z\\d]{3,15}[.][a-zA-Z]{2,5}")||
                requestDto.getPassword().matches("^(?=./*[A-Za-z\\d@$!%*#?&]){4,18}$")){
             throw new CustomException(ErrorCode.BAD_REQUEST);
        }
    }
}
