package com.hanghae.greenstep.clap;

import com.hanghae.greenstep.feed.Feed;
import com.hanghae.greenstep.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClapRepository extends JpaRepository<Clap,Long> {
    Boolean findByMemberAndFeed(Member member, Feed feed);
}
