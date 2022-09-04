package com.hanghae.greenstep.mission;

import com.hanghae.greenstep.image.ImageService;
import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.missionStatus.MissionStatus;
import com.hanghae.greenstep.missionStatus.MissionStatusRepository;
import com.hanghae.greenstep.shared.Check;
import com.hanghae.greenstep.shared.Message;
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

import static com.hanghae.greenstep.shared.Status.WAITING;

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
        check.accessTokenCheck(request);
        List<Mission> missionList = missionRepository.findDailyMissionByOnShow();
        return getResponseEntity(missionList);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getWeeklyMissions(HttpServletRequest request) {
        check.accessTokenCheck(request);
        List<Mission> missionList = missionRepository.findWeeklyMissionByOnShow();
        return getResponseEntity(missionList);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getTodayMission(HttpServletRequest request) {
        check.accessTokenCheck(request);
        List<Mission> missionList = missionRepository.findTodayMissionByOnShow();
        return getResponseEntity(missionList);
    }

    private ResponseEntity<?> getResponseEntity(List<Mission> missionList) {
        List<MissionResponseDto> missionResponseDtoList = new ArrayList<>();
        for (Mission mission : missionList) {
            missionResponseDtoList.add(
                    MissionResponseDto.builder()
                            .missionId(mission.getId())
                            .missionName(mission.getMissionName())
                            .missionContent(mission.getMissionContent())
                            .missionImageUrl(mission.getMissionImageUrl())
                            .missionType(mission.getMissionType())
                            .missionName(mission.getMissionName())
                            .onShow(mission.getOnShow())
                            .build()
            );
        }
        return new ResponseEntity<>(Message.success(missionResponseDtoList), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getMissionDetail(Long missionId, HttpServletRequest request) throws Exception {
        check.accessTokenCheck(request);
        Mission mission = missionRepository.findById(missionId).orElseThrow(() -> new Exception("미션이 없습니다."));
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
        Mission mission = missionRepository.findById(missionId).orElseThrow(() -> new Exception("미션이 없습니다."));
        MissionStatus missionStatus = MissionStatus.builder()
                .member(member)
                .mission(mission)
                .missionStatus(WAITING)
                .build();
        missionStatusRepository.save(missionStatus);
        String imgUrl = imageService.getImgUrlBase64(missionRequestDto.getBase64String());
        SubmitMission submitMission = SubmitMission.builder()
                .imgUrl(imgUrl)
                .mission(mission)
                .member(member)
                .status(WAITING)
                .build();
        submitMissionRepository.save(submitMission);
        return new ResponseEntity<>(Message.success("전송 완료"),HttpStatus.OK);
    }
}
