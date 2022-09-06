package com.hanghae.greenstep.member;


import com.hanghae.greenstep.shared.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByKakaoId(Long kakaoId);

    Optional<Member> findByEmail(String email);
    
    List<Member> findTop3ByOrderByDailyMissionPointDesc();

    Optional<Member> findByEmailAndRole(String email, Authority authority);
}