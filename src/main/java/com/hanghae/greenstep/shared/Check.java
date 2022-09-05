package com.hanghae.greenstep.shared;

import com.hanghae.greenstep.exception.CustomException;
import com.hanghae.greenstep.exception.ErrorCode;
import com.hanghae.greenstep.feed.Feed;
import com.hanghae.greenstep.jwt.TokenProvider;
import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.submitMission.SubmitMission;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class Check {

    private final TokenProvider tokenProvider;

    public Member accessTokenCheck(HttpServletRequest request) {
        if (null == request.getHeader("Authorization")) throw new CustomException(ErrorCode.TOKEN_IS_EXPIRED);
        if (tokenProvider.validateToken(request.getHeader("Authorization").substring(7))) {
            return tokenProvider.getMemberFromAuthentication();
        }
        throw new CustomException(ErrorCode.INVALID_TOKEN);
    }

    public void checkMember(SubmitMission submitMission, Member member) {
        if (!member.equals(submitMission.getMember())) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
    }

    public void checkMember(Feed feed, Member member) {
        if (!member.equals(feed.getMember())) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
    }
}
