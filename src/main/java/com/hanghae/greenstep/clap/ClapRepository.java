package com.hanghae.greenstep.clap;


import com.hanghae.greenstep.feed.Feed;
import com.hanghae.greenstep.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface ClapRepository extends JpaRepository<Clap,Long> {
    Boolean findByMemberAndFeed(Member member, Feed feed);
    Optional<Clap> findByMember_IdAndFeed(Long memberId, Feed feed);
    Integer countByFeed(Feed feed);
}
