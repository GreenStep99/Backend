package com.hanghae.greenstep.shared;

import com.hanghae.greenstep.jwt.UserDetailsImpl;
import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommonService {
    private final MemberRepository memberRepository;

    public Member getMember() {
        log.info("===========================1-1고승유=====================================================");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        String username = principal.getMember().getName();
        log.info("===========================1-2고승유=====================================================");

        return memberRepository.findByName(username).orElseThrow(
                () -> new UsernameNotFoundException("존재하지 않는 유저입니다")
        );
    }
}


//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
//        String username = principal.getUser().getUsername();
//        com.project.dogfaw.user.model.User user = userRepository.findByUsername(username).orElseThrow(
//                () -> new UsernameNotFoundException("존재하지 않는 유저입니다")
//        );
