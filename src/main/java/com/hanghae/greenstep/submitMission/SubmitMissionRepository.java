package com.hanghae.greenstep.submitMission;

import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.mission.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubmitMissionRepository extends JpaRepository<SubmitMission,Long> {

    @Query("select s from SubmitMission s join fetch s.member order by s.createdAt asc")
    List<SubmitMission> findAllByOrderByCreatedAtAscFetchJoin();

    @Query("select s from SubmitMission s join fetch s.member where s.id = :submitMissionId")
    Optional<SubmitMission> findByIdFetchJoin(@Param("submitMissionId") Long submitMissionId);

    @Query("select s from SubmitMission s where s.status = 'DONE' AND s.member = :member AND s.onHide = :onHide order by s.createdAt desc ")
    List<SubmitMission> findAllByMemberAndIsHidden(@Param("member") Member member, @Param("onHide") Boolean onHide);
}
