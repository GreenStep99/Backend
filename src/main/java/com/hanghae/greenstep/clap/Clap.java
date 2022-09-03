package com.hanghae.greenstep.clap;

import com.hanghae.greenstep.feed.Feed;
import com.hanghae.greenstep.member.Member;
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

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Feed feed;


    public Clap(ClapRequestDto clapRequestDto) {
        if (clapRequestDto.getMember() != null) this.member = clapRequestDto.getMember();
        if (clapRequestDto.getFeed() != null) this.feed = clapRequestDto.getFeed();
    }
}
