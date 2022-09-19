package com.hanghae.greenstep.feed;


import com.hanghae.greenstep.member.Member;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed,Long> {
    List<Feed> findByIdLessThanAndOnHideOrderByIdDesc(Long lastFeedId,Boolean onHide, PageRequest pageRequest);
    List<Feed> findByIdLessThanAndOnHideAndTagOrderByIdDesc(Long lastFeedId,Boolean onHide, String tag, PageRequest pageRequest);
    @Query("select f from Feed f join fetch f.member where f.member = :member and f.onHide = :onHide order by f.createdAt desc ")
    List<Feed> findAllByMemberFetchJoin(@Param("member") Member member, @Param("onHide") Boolean onHide);

    Feed findBySubmitMissionId(Long submitMissionId);

    Boolean existsBySubmitMissionId(Long submitMissionId);
}

