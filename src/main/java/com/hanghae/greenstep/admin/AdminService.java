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
import com.hanghae.greenstep.submitMission.SubmitMission;
import com.hanghae.greenstep.submitMission.SubmitMissionRepository;
import com.hanghae.greenstep.submitMission.SubmitMissionResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.hanghae.greenstep.shared.Authority.ROLE_ADMIN;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final SubmitMissionRepository submitMissionRepository;
    private final MissionStatusRepository missionStatusRepository;
    private final Check check;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    ApplicationEventPublisher publisher;

    //n:1
    @Transactional(readOnly=true)
    public ResponseEntity<?> getSubmitMission(HttpServletRequest request) {
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

    //n+1 문제 없음
    public ResponseEntity<?> login(AdminLoginRequestDto adminLoginRequestDto, HttpServletResponse response) {
        log.info("요청값?"+adminLoginRequestDto);
        Member admin = memberRepository.findByEmailAndRole(adminLoginRequestDto.getEmail(), ROLE_ADMIN).orElseThrow(
                () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        if (!admin.validatePassword(passwordEncoder, adminLoginRequestDto.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_MEMBER_INFO);
        }
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

    //n:1
    @Transactional
    public ResponseEntity<?> verifySubmitMission(Status verification, Long submitMissionId, HttpServletRequest request, String info) {
        Member admin = check.accessTokenCheck(request);
        check.checkAdmin(admin);

        SubmitMission submitMission = submitMissionRepository.findByIdFetchJoin(submitMissionId).orElseThrow(
                        () -> new CustomException(ErrorCode.MISSION_NOT_FOUND)
                );
        if(submitMission.getMember().getAcceptMail()) publisher.publishEvent(new VerifiedEvent(verification,submitMission,info));

        changeMissionStatus(verification, submitMission, admin, info);
        earnMissionPoints(submitMission);
        SubmitMissionResponseDto submitMissionResponseDto = new SubmitMissionResponseDto(submitMission);
        return new ResponseEntity<>(Message.success(submitMissionResponseDto), HttpStatus.OK);
    }

    //n+1 문제 없음
    public void changeMissionStatus(Status verification, SubmitMission submitMission, Member admin, String info) {
        submitMission.update(verification, info, admin.getName());
        MissionStatus missionStatus = missionStatusRepository.findByMemberAndMission(submitMission.getMember(), submitMission.getMission())
                .orElseThrow(() -> new CustomException(ErrorCode.MISSION_STATUS_NOT_FOUND));
        missionStatus.update(verification);
    }

    public void earnMissionPoints(SubmitMission submitMission) {
        if (Objects.equals(submitMission.getMissionType(), "daily"))
            submitMission.getMember().earnDailyPoint();
        if (Objects.equals(submitMission.getMissionType(), "weekly"))
            submitMission.getMember().earnWeeklyPoint();
        submitMission.getMember().earnChallengePoint();
    }

}
