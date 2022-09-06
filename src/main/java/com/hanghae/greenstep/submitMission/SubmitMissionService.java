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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubmitMissionService {

    private final Check check;
    private final SubmitMissionRepository submitMissionRepository;

    public ResponseEntity<?> getMyMissions(HttpServletRequest request) {
        Member member = check.accessTokenCheck(request);
        List<SubmitMission> submitMissionList = submitMissionRepository.findAllByMember(member);
        List<MyMissionsDto> myMissionsDtoList = new ArrayList<>();
        for(SubmitMission submitMission:submitMissionList){
            MyMissionsDto myMissionsDto =new MyMissionsDto(submitMission);
            myMissionsDtoList.add(myMissionsDto);
        }
        return new ResponseEntity<>(Message.success(myMissionsDtoList), HttpStatus.OK);
    }
}
