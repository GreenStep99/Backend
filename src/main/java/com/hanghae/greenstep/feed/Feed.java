package com.hanghae.greenstep.feed;

import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.shared.Timestamped;
import lombok.Builder;
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

    @Column(length = 280)
    private String content;

    @Column
    private Integer clapCount;

    @Column
    private String tag;

    @Builder
    public Feed(Member member, String missionName, String imgUrl, String content, String tag){
        this.id = getId();
        this.member = member;
        this.missionName = missionName;
        this.imgUrl = imgUrl;
        this.content = content;
        this.clapCount = 0;
        this.tag = tag;
    }

    public void update(Integer clapCount){
        this.clapCount = clapCount;
    }
}
