package com.hanghae.greenstep.rank;

import com.hanghae.greenstep.rank.Dto.MemberRankResponseDto;
import com.hanghae.greenstep.shared.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class RankController {
    private final RankService rankService;
    @GetMapping("/rank/mission/point/daily")
    public ResponseEntity<?> getDailyRankMissionPoint() {
        List<MemberRankResponseDto> memberRankResponseDtoList = rankService.getDailyRankMissionPoint();
        return new ResponseEntity<>(Message.success(memberRankResponseDtoList), HttpStatus.OK);
    }
}
