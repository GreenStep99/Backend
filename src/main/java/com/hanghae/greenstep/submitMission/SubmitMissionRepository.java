package com.hanghae.greenstep.submitMission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubmitMissionRepository extends JpaRepository<SubmitMission,Long> {
    List<SubmitMission> findAllByOrderByCreatedAtAsc();
    @Query(value = "select s.member from SubmitMission s where s.status = 'done' group by s.member order by count(s) desc")
    List<SubmitMission> findRankMissions();
}
