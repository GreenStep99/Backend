package com.hanghae.greenstep.mission;

import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.mission.Dto.MissionResponseDto;
import com.hanghae.greenstep.shared.Check;
import com.hanghae.greenstep.shared.Status;
import com.hanghae.greenstep.submitMission.MissionStatus;
import com.hanghae.greenstep.submitMission.MissionStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.hanghae.greenstep.shared.Status.DEFAULT;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final MissionRepository missionRepository;

    private final MissionStatusRepository missionStatusRepository;

    private final Check check;

    //n+1문제 없음
    @Transactional(readOnly = true)
    public List<MissionResponseDto> getDailyMissions(HttpServletRequest request) {
        Member member = check.accessTokenCheck(request);
        List<Mission> missionList = missionRepository.findDailyMissionByOnShow();
        return getMissionResponseDto(missionList, member);
    }

    //n+1문제 없음
    @Transactional(readOnly = true)
    public List<MissionResponseDto> getWeeklyMissions(HttpServletRequest request) {
        Member member = check.accessTokenCheck(request);
        List<Mission> missionList = missionRepository.findWeeklyMissionByOnShow();
        return getMissionResponseDto(missionList, member);
    }

    //n+1문제 없음
    @Transactional(readOnly = true)
    public List<MissionResponseDto> getTodayMission(HttpServletRequest request) {
        Member member = check.accessTokenCheck(request);
        List<Mission> missionList = missionRepository.findTodayMissionByOnShow();
        return getMissionResponseDto(missionList, member);
    }

    private List<MissionResponseDto> getMissionResponseDto(List<Mission> missionList, Member member) {
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
                            .missionIconUrl(mission.getMissionIconUrl())
                            .missionImageUrl(mission.getMissionImageUrl())
                            .missionType(mission.getMissionType())
                            .onShow(mission.getOnShow())
                            .status(status)
                            .tag(mission.getTag())
                            .build()
            );
        }
        return missionResponseDtoList;
    }

}
