package com.hanghae.greenstep.feed;


import com.hanghae.greenstep.member.Member;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed,Long> {
    List<Feed> findByIdLessThanOrderByIdDesc(Long lastFeedId, PageRequest pageRequest);
    List<Feed> findByIdLessThanAndTagOrderByIdDesc(Long lastFeedId, String tag, PageRequest pageRequest);

    @Query("select s from Feed s where s.member = :member order by s.createdAt desc ")
    List<Feed> findAllByMember(@Param("member") Member member);
}

