package com.hanghae.greenstep.member;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.hanghae.greenstep.exception.CustomException;
import com.hanghae.greenstep.exception.ErrorCode;
import com.hanghae.greenstep.kakaoAPI.Dto.KakaoMemberInfoDto;
import com.hanghae.greenstep.kakaoAPI.Dto.KakaoPhotoDto;
import com.hanghae.greenstep.kakaoAPI.KakaoSocialService;
import com.hanghae.greenstep.member.Dto.MemberRequestDto;
import com.hanghae.greenstep.member.Dto.MemberResponseDto;
import com.hanghae.greenstep.shared.Check;
import com.hanghae.greenstep.submitMission.SubmitMissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Arrays;

import static com.hanghae.greenstep.shared.Status.DONE;
import static com.hanghae.greenstep.shared.Status.WAITING;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final Check check;
    private final MemberRepository memberRepository;
    private final SubmitMissionRepository submitMissionRepository;
    private final KakaoSocialService kakaoSocialService;

    @Transactional
    public MemberResponseDto updateMemberInfo(MemberRequestDto memberRequestDto, HttpServletRequest request) {
        Member member = memberRepository.findByEmail(check.accessTokenCheck(request).getEmail()).orElseThrow(
                () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND)
        );
        if(!memberRequestDto.getNickname().matches("^[a-zA-Z가-힣\\d]{1,8}$")||
                !memberRequestDto.getName().matches("^[가-힣]{2,6}$"))
            throw new CustomException(ErrorCode.INVALID_INPUT);
        member.update(memberRequestDto);
        return new MemberResponseDto(member);
    }

    @Transactional(readOnly=true)
    public MemberResponseDto getMemberInfo(HttpServletRequest request) {
        Member member =check.accessTokenCheck(request);
        int missionCount = submitMissionRepository.countByMemberAndStatus(member, DONE);
        int waitingMissionCount = submitMissionRepository.countByMemberAndStatus(member, WAITING);
        return new MemberResponseDto(member, missionCount, waitingMissionCount);}

    public KakaoPhotoDto getKakaoPhoto(HttpServletRequest request) throws JsonProcessingException {
        String accessToken = request.getHeader("Kakao_Authorization");
        KakaoMemberInfoDto kakaoMemberInfoDto = kakaoSocialService.getKakaoUserInfo(accessToken);
        return new KakaoPhotoDto(kakaoMemberInfoDto.getProfilePhoto());
    }
}
