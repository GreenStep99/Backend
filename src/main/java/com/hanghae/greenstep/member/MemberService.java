package com.hanghae.greenstep.member;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.hanghae.greenstep.exception.CustomException;
import com.hanghae.greenstep.exception.ErrorCode;
import com.hanghae.greenstep.jwt.TokenProvider;
import com.hanghae.greenstep.kakaoAPI.Dto.KakaoMemberInfoDto;
import com.hanghae.greenstep.kakaoAPI.Dto.KakaoPhotoDto;
import com.hanghae.greenstep.kakaoAPI.KakaoSocialService;
import com.hanghae.greenstep.member.Dto.MemberRequestDto;
import com.hanghae.greenstep.member.Dto.MemberResponseDto;
import com.hanghae.greenstep.shared.Check;
import com.hanghae.greenstep.submitMission.SubmitMissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final TokenProvider tokenProvider;
    private final Check check;
    private final MemberRepository memberRepository;
    private final SubmitMissionRepository submitMissionRepository;
    private final KakaoSocialService kakaoSocialService;

    @Transactional
    public MemberResponseDto updateMemberInfo(MemberRequestDto memberRequestDto, HttpServletRequest request) {
        Member member = memberRepository.findByEmail(check.accessTokenCheck(request).getEmail()).orElseThrow(
                () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND)
        );
        member.update(memberRequestDto);
        return new MemberResponseDto(member);
    }

    @Transactional(readOnly=true)
    public MemberResponseDto getMemberInfo(HttpServletRequest request) {
        Member member =check.accessTokenCheck(request);
        int missionCount = submitMissionRepository.countByMember(member);
        return new MemberResponseDto(member, missionCount);}


    public KakaoPhotoDto getKakaoPhoto(HttpServletRequest request) throws JsonProcessingException {
        String accessToken = request.getHeader("Kakao_Authorization");
        KakaoMemberInfoDto kakaoMemberInfoDto = kakaoSocialService.getKakaoUserInfo(accessToken);
        return new KakaoPhotoDto(kakaoMemberInfoDto.getProfilePhoto());
    }
}
