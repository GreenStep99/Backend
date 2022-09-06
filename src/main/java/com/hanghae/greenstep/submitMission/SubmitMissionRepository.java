package com.hanghae.greenstep.submitMission;

import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.mission.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubmitMissionRepository extends JpaRepository<SubmitMission,Long> {
    List<SubmitMission> findAllByOrderByCreatedAtAsc();

    @Query("select s from SubmitMission s where s.status = 'DONE' AND s.member = :member order by s.createdAt desc ")
    List<SubmitMission> findAllByMember(@Param("member") Member member);

    @Query("select s from SubmitMission s where s.mission = :mission AND s.member = :member AND s.status = 'DONE' order by s.createdAt")
    List<SubmitMission> findAllByMemberAndMission(Member member, Mission mission);
}
