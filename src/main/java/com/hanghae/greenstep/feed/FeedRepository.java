package com.hanghae.greenstep.feed;


import com.hanghae.greenstep.member.Member;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FeedRepository extends JpaRepository<Feed,Long> {
    List<Feed> findByIdAndOnHideLessThanOrderByIdDesc(Long lastFeedId,Boolean onHide, PageRequest pageRequest);
    List<Feed> findByIdAndOnHideLessThanAndTagOrderByIdDesc(Long lastFeedId,Boolean onHide, String tag, PageRequest pageRequest);
    @Query("select f from Feed f join fetch f.member where f.member = :member and f.onHide = :onHide order by f.createdAt desc ")
    List<Feed> findAllByMemberFetchJoin(@Param("member") Member member, @Param("onHide") Boolean onHide);
    Optional<Feed> findById(Long aLong);
    Feed findBySubmitMissionId(Long submitMissionId);

    Boolean existsBySubmitMissionId(Long submitMissionId);
}

