package com.hanghae.greenstep.mission;

import com.hanghae.greenstep.exception.CustomException;
import com.hanghae.greenstep.exception.ErrorCode;
import com.hanghae.greenstep.image.ImageService;
import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.missionStatus.MissionStatus;
import com.hanghae.greenstep.missionStatus.MissionStatusRepository;
import com.hanghae.greenstep.shared.Check;
import com.hanghae.greenstep.shared.Message;
import com.hanghae.greenstep.shared.Status;
import com.hanghae.greenstep.submitMission.SubmitMission;
import com.hanghae.greenstep.submitMission.SubmitMissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.hanghae.greenstep.shared.Status.*;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final MissionRepository missionRepository;

    private final MissionStatusRepository missionStatusRepository;

    private final SubmitMissionRepository submitMissionRepository;

    private final ImageService imageService;

    private final Check check;

    @Transactional(readOnly = true)
    public ResponseEntity<?> getDailyMissions(HttpServletRequest request) {
        Member member = check.accessTokenCheck(request);
        List<Mission> missionList = missionRepository.findDailyMissionByOnShow();
        return getResponseEntity(missionList, member);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getWeeklyMissions(HttpServletRequest request) {
        Member member = check.accessTokenCheck(request);
        List<Mission> missionList = missionRepository.findWeeklyMissionByOnShow();
        return getResponseEntity(missionList, member);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getTodayMission(HttpServletRequest request) {
        Member member = check.accessTokenCheck(request);
        List<Mission> missionList = missionRepository.findTodayMissionByOnShow();
        return getResponseEntity(missionList, member);
    }

    private ResponseEntity<?> getResponseEntity(List<Mission> missionList, Member member) {
        List<MissionResponseDto> missionResponseDtoList = new ArrayList<>();
        for (Mission mission : missionList) {
            Optional<MissionStatus> missionStatus = missionStatusRepository.findByMemberAndMission(member, mission);
            Status status = DEFAULT;
            if (missionStatus.isPresent()) status = missionStatus.get().getMissionStatus();
            missionResponseDtoList.add(
                    MissionResponseDto.builder()
                            .missionId(mission.getId())
                            .missionName(mission.getMissionName())
                            .missionContent(mission.getMissionContent())
                            .missionImageUrl(mission.getMissionImageUrl())
                            .missionType(mission.getMissionType())
                            .missionName(mission.getMissionName())
                            .onShow(mission.getOnShow())
                            .status(status)
                            .tag(mission.getTag())
                            .build()
            );
        }
        return new ResponseEntity<>(Message.success(missionResponseDtoList), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getMissionDetail(Long missionId, HttpServletRequest request) {
        check.accessTokenCheck(request);
        Mission mission = missionRepository.findById(missionId).orElseThrow(() -> new CustomException(ErrorCode.MISSION_NOT_FOUND));
        return new ResponseEntity<>(Message.success(MissionResponseDto.builder()
                .missionId(mission.getId())
                .missionName(mission.getMissionName())
                .missionImageUrl(mission.getMissionImageUrl())
                .missionContent(mission.getMissionContent())
                .missionType(mission.getMissionType())
                .missionName(mission.getMissionName())
                .onShow(mission.getOnShow())
                .build()), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> submitMission(Long missionId, HttpServletRequest request, MissionRequestDto missionRequestDto) throws Exception {
        Member member = check.accessTokenCheck(request);
        Mission mission = missionRepository.findById(missionId).orElseThrow(() -> new CustomException(ErrorCode.MISSION_NOT_FOUND));
        MissionStatus missionStatus = missionStatusRepository.findByMemberAndMission(member, mission)
                .orElse(MissionStatus.builder()
                        .member(member)
                        .mission(mission)
                        .missionStatus(WAITING)
                        .missionType(mission.getMissionType())
                        .build());
        if(missionStatus.getMissionStatus() == REJECTED) missionStatus.update(WAITING);
        missionStatusRepository.save(missionStatus);
        String imgUrl = imageService.getImgUrlBase64(missionRequestDto.getBase64String());
        SubmitMission submitMission = SubmitMission.builder()
                .imgUrl(imgUrl)
                .mission(mission)
                .member(member)
                .status(WAITING)
                .missionName(mission.getMissionName())
                .missionType(mission.getMissionType())
                .build();
        submitMissionRepository.save(submitMission);
        return new ResponseEntity<>(Message.success("전송 완료"),HttpStatus.OK);
    }
}
