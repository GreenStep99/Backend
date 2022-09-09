package com.hanghae.greenstep.clap;

import com.hanghae.greenstep.feed.Feed;
import com.hanghae.greenstep.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClapRepository extends JpaRepository<Clap,Long> {
    boolean existsByMemberAndFeed(Member member, Feed feed);
    Optional<Clap> findByMemberAndFeed(Member member, Feed feed);
    Integer countByFeed(Feed feed);
}
