package com.hanghae.greenstep.mission;

import com.hanghae.greenstep.shared.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final MissionRepository missionRepository;

    @Transactional
    public ResponseEntity<?> getMissions() {
        List<Mission> missionList = missionRepository.findAll();
        List<MissionResponseDto> missionResponseDtoList = new ArrayList<>();
        for(Mission mission : missionList){
            missionResponseDtoList.add(
                    MissionResponseDto.builder()
                            .missionId(mission.getId())
                            .missionContent(mission.getMissionContent())
                            .missionImageUrl(mission.getMissionImageUrl())
                            .missionType(mission.getMissionType())
                            .onShow(mission.getOnShow())
                            .build()
            );
        }

        return new ResponseEntity<>(Message.success(missionResponseDtoList), HttpStatus.OK);
    }
}
