package com.hanghae.greenstep.submitMission;

import com.hanghae.greenstep.exception.CustomException;
import com.hanghae.greenstep.exception.ErrorCode;
import com.hanghae.greenstep.image.ImageService;
import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.mission.Mission;
import com.hanghae.greenstep.mission.MissionRepository;
import com.hanghae.greenstep.mission.MissionRequestDto;
import com.hanghae.greenstep.missionStatus.MissionStatus;
import com.hanghae.greenstep.missionStatus.MissionStatusRepository;
import com.hanghae.greenstep.shared.Check;
import com.hanghae.greenstep.shared.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.hanghae.greenstep.shared.Status.REJECTED;
import static com.hanghae.greenstep.shared.Status.WAITING;

@Service
@RequiredArgsConstructor
public class SubmitMissionService {

    private final Check check;
    private final SubmitMissionRepository submitMissionRepository;
    private final MissionRepository missionRepository;
    private final MissionStatusRepository missionStatusRepository;
    private final ImageService imageService;

    @Transactional(readOnly=true)
    public ResponseEntity<?> getMyMissions(HttpServletRequest request) {
        Member member = check.accessTokenCheck(request);
        List<SubmitMission> submitMissionList = submitMissionRepository.findAllByMemberAndOnHide(member, false);
        return getMyMissionDtoList(submitMissionList);
    }

    @Transactional(readOnly=true)
    public ResponseEntity<?> getHiddenMissions(HttpServletRequest request) {
        Member member = check.accessTokenCheck(request);
        List<SubmitMission> submitMissionList = submitMissionRepository.findAllByMemberAndOnHide(member, true);
        return getMyMissionDtoList(submitMissionList);
    }

    private ResponseEntity<?> getMyMissionDtoList(List<SubmitMission> submitMissionList) {
        List<MyMissionsDto> myMissionsDtoList = new ArrayList<>();
        for(SubmitMission submitMission:submitMissionList){
            MyMissionsDto myMissionsDto =new MyMissionsDto(submitMission);
            myMissionsDtoList.add(myMissionsDto);
        }
        return new ResponseEntity<>(Message.success(myMissionsDtoList), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> hideMyMissions(Long[] missionsIdList, HttpServletRequest request) {
        Member member =check.accessTokenCheck(request);
        List<MyMissionsDto> myMissionsDtoList = new ArrayList<>();
        for(Long submitMissionId : missionsIdList) {
            SubmitMission submitMission = submitMissionRepository.findById(submitMissionId).orElseThrow(
                    () -> new CustomException(ErrorCode.POST_NOT_FOUND)
            );
            check.checkMember(submitMission, member);
            submitMission.toggleOnHide();
            MyMissionsDto myMissionsDto =new MyMissionsDto(submitMission);
            myMissionsDtoList.add(myMissionsDto);
        }
        return new ResponseEntity<>(Message.success(myMissionsDtoList),HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> submitMission(Long missionId, HttpServletRequest request, MissionRequestDto missionRequestDto) throws Exception {
        Member member = check.accessTokenCheck(request);
        Mission mission = missionRepository.findById(missionId).orElseThrow(() -> new CustomException(ErrorCode.MISSION_NOT_FOUND));
        Optional<MissionStatus> missionStatus = missionStatusRepository.findByMemberAndMission(member, mission);
        if(missionStatus.isPresent()){
            if(missionStatus.get().getMissionStatus() == REJECTED) missionStatus.get().update(WAITING);
            else throw new CustomException(ErrorCode.BAD_REQUEST);
        } else {
            MissionStatus newMissionStatus = MissionStatus.builder()
                    .member(member)
                    .mission(mission)
                    .missionStatus(WAITING)
                    .missionType(mission.getMissionType())
                    .build();
            missionStatusRepository.save(newMissionStatus);
        }
        String imgUrl = imageService.getImgUrlBase64(missionRequestDto.getBase64String());
        SubmitMission submitMission = SubmitMission.builder()
                .imgUrl(imgUrl)
                .mission(mission)
                .member(member)
                .status(WAITING)
                .mission(mission)
                .build();
        submitMissionRepository.save(submitMission);
        return new ResponseEntity<>(Message.success("전송 완료"),HttpStatus.OK);
    }
}
