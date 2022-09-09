package com.hanghae.greenstep.feed;


import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.submitMission.SubmitMission;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FeedRepository extends JpaRepository<Feed,Long> {
    List<Feed> findByIdLessThanOrderByIdDesc(Long lastFeedId, PageRequest pageRequest);
    List<Feed> findByIdLessThanAndTagOrderByIdDesc(Long lastFeedId, String tag, PageRequest pageRequest);
    @Query("select f from Feed f join fetch f.member where f.member = :member order by f.createdAt desc ")
    List<Feed> findAllByMemberFetchJoin(@Param("member") Member member);
}

