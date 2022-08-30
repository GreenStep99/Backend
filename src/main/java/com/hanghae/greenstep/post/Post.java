package com.hanghae.greenstep.post;

import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.shared.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Post extends Timestamped {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length=80)
    private String missionName;

    @Column
    private String imgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}
