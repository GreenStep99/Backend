package com.hanghae.greenstep.rank;

import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.member.MemberRepository;
import com.hanghae.greenstep.shared.Message;
import com.hanghae.greenstep.submitMission.SubmitMission;
import com.hanghae.greenstep.submitMission.SubmitMissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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
    private final SubmitMissionRepository submitMissionRepository;

    @Transactional
    public ResponseEntity<?> getRankMissions() {
        PageRequest pageRequest = PageRequest.of(0, 3);
        List<SubmitMission> memberRankList = submitMissionRepository.findRankMissions(pageRequest);
        List<MemberRankResponseDto> memberRankResponseDtoList = makeMissionRankList(memberRankList);
        return new ResponseEntity<>(Message.success(memberRankResponseDtoList), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> getRankMissionPoint(){
        List<Member> memberRankList = memberRepository.findTop3ByOrderByMissionPointDesc();
        List<MemberRankResponseDto> memberRankResponseDtoList = makePointRankList(memberRankList);
        return new ResponseEntity<>(Message.success(memberRankResponseDtoList), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> getDailyRankMissionPoint(){
        List<Member> memberDailyRankList = memberRepository.findTop3ByOrderByDailyMissionPointDesc();
        List<MemberRankResponseDto> memberRankResponseDtoList = makePointRankList(memberDailyRankList);
        return new ResponseEntity<>(Message.success(memberRankResponseDtoList), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> getWeeklyRankMissionPoint(){
        List<Member> memberWeeklyRankList = memberRepository.findTop3ByOrderByWeeklyMissionPointDesc();
        List<MemberRankResponseDto> memberRankResponseDtoList = makePointRankList(memberWeeklyRankList);
        return new ResponseEntity<>(Message.success(memberRankResponseDtoList), HttpStatus.OK);
    }

    public List<MemberRankResponseDto> makeMissionRankList(List<SubmitMission> memberRankList){
        List<MemberRankResponseDto> memberRankResponseDtoList =new ArrayList<>();
        for (SubmitMission submitMission : memberRankList) {
            memberRankResponseDtoList.add(
                    MemberRankResponseDto.builder()
                            .profilePhoto(submitMission.getMember().getProfilePhoto())
                            .nickName(submitMission.getMember().getNickname())
                            .name(submitMission.getMember().getName())
                            .build()
            );
        }
        return memberRankResponseDtoList;
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
