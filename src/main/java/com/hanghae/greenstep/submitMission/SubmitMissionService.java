package com.hanghae.greenstep.submitMission;

import com.hanghae.greenstep.exception.CustomException;
import com.hanghae.greenstep.exception.ErrorCode;
import com.hanghae.greenstep.feed.Feed;
import com.hanghae.greenstep.member.Member;
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

@Service
@RequiredArgsConstructor
public class SubmitMissionService {

    private final Check check;
    private final SubmitMissionRepository submitMissionRepository;

    @Transactional(readOnly=true)
    public ResponseEntity<?> getMyMissions(HttpServletRequest request) {
        Member member = check.accessTokenCheck(request);
        List<SubmitMission> submitMissionList = submitMissionRepository.findAllByMemberAndIsHidden(member, false);
        return getMyMissionDtoList(submitMissionList);
    }

    @Transactional(readOnly=true)
    public ResponseEntity<?> getHiddenMissions(HttpServletRequest request) {
        Member member = check.accessTokenCheck(request);
        List<SubmitMission> submitMissionList = submitMissionRepository.findAllByMemberAndIsHidden(member, true);
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

}
