package com.hanghae.greenstep.rank;

import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.member.MemberRepository;
import com.hanghae.greenstep.shared.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RankService {
    private final MemberRepository memberRepository;

    @Transactional
    public ResponseEntity<?> getDailyRankMissionPoint(){
        List<Member> memberDailyRankList = memberRepository.findTop3ByOrderByDailyMissionPointDesc();
        List<MemberRankResponseDto> memberRankResponseDtoList = makePointRankList(memberDailyRankList);
        return new ResponseEntity<>(Message.success(memberRankResponseDtoList), HttpStatus.OK);
    }

    public List<MemberRankResponseDto> makePointRankList(List<Member> memberRankList){
        List<MemberRankResponseDto> memberRankResponseDtoList =new ArrayList<>();
        for (Member member : memberRankList) {
            memberRankResponseDtoList.add(
                    MemberRankResponseDto.builder()
                            .profilePhoto(member.getProfilePhoto())
                            .nickName(member.getNickname())
                            .name(member.getName())
                            .build()
            );
        }
        return memberRankResponseDtoList;
    }
}
