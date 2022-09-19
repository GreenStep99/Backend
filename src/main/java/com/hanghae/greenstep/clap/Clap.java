package com.hanghae.greenstep.clap;

import com.hanghae.greenstep.clap.Dto.ClapRequestDto;
import com.hanghae.greenstep.feed.Feed;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Clap {

    @Id
    @GeneratedValue
    private Long id;

    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Feed feed;

    public Clap(ClapRequestDto clapRequestDto) {
        if (clapRequestDto.getMember().getId() != null) this.memberId = clapRequestDto.getMember().getId();
        if (clapRequestDto.getFeed() != null) this.feed = clapRequestDto.getFeed();
    }
}
