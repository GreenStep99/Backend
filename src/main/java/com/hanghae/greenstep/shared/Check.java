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

import static com.hanghae.greenstep.shared.Authority.ROLE_ADMIN;

@Component
@RequiredArgsConstructor
public class Check {
    private final TokenProvider tokenProvider;

    public Member accessTokenCheck(HttpServletRequest request) {
        if (null == request.getHeader("Authorization")|| request.getHeader("Authorization").length() < 7) throw new CustomException(ErrorCode.INVALID_TOKEN);
        if (tokenProvider.validateToken(request.getHeader("Authorization").substring(7))) {
            return tokenProvider.getMemberFromAuthentication();
        }
        throw new CustomException(ErrorCode.INVALID_TOKEN);
    }

    public void checkAdmin(Member member) {
        if(member.getRole()!= ROLE_ADMIN) throw new CustomException(ErrorCode.MEMBER_NOT_ALLOWED);
    }

    public void checkMember(SubmitMission submitMission, Member member) {
        if (!submitMission.getMember().equals(member)) {
            throw new CustomException(ErrorCode.NOT_AUTHOR);
        }
    }

    public void checkMember(Feed feed, Member member) {
        if (!feed.getMember().equals(member)) {
            throw new CustomException(ErrorCode.NOT_AUTHOR);
        }
    }


}
