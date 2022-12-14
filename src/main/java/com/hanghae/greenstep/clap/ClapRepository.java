package com.hanghae.greenstep.clap;

import com.hanghae.greenstep.feed.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClapRepository extends JpaRepository<Clap,Long> {
    boolean existsByMemberIdAndFeed(Long memberId, Feed feed);
    Optional<Clap> findByMemberIdAndFeed(Long memberId, Feed feed);
    Integer countByFeed(Feed feed);
}
