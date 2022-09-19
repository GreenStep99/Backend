package com.hanghae.greenstep.clap.Dto;


import com.hanghae.greenstep.feed.Feed;
import com.hanghae.greenstep.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClapRequestDto {
    private Member member;
    private Feed feed;
}