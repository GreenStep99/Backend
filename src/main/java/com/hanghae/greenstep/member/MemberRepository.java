package com.hanghae.greenstep.member;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByKakaoId(Long kakaoId);

    Optional<Member> findByEmail(String email);

    List<Member> findAllByOrderByMissionPointDesc();
    List<Member> findAllByOrderByDailyMissionPointDesc();
    List<Member> findAllByOrderByWeeklyMissionPointDesc();
}