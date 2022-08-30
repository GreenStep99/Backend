package com.hanghae.greenstep.feed;

import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.shared.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Feed extends Timestamped {

    @GeneratedValue
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(length = 80)
    private String missionName;

    @Column
    private String imgUrl;

    @Column(columnDefinition = "TEXT", length = 280)
    private String content;

    @Column
    private Integer clapCount;
}
