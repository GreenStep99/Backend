package com.hanghae.greenstep.submitMission;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmitMissionRepository extends JpaRepository<SubmitMission,Long> {
    List<SubmitMission> findAllByOrderByCreatedAtAsc();
}
