package com.hanghae.greenstep.feed;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed,Long> {
    List<Feed> findByIdLessThanInOrderByIdDesc(int lastFeedId, PageRequest pageRequest);
}

