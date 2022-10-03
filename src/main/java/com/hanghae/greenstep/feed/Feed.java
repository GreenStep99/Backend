package com.hanghae.greenstep.feed;

import com.hanghae.greenstep.clap.Clap;
import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.shared.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Feed extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "feed", orphanRemoval = true)
    private List<Clap> claps;

    @Column
    private String tag;

    @Column(unique = true)
    private Long submitMissionId;

    @Column
    private Boolean onHide;

    @Builder
    public Feed(Member member, String missionName, String imgUrl, String content, String tag, Long submitMissionId){
        this.id = getId();
        this.member = member;
        this.missionName = missionName;
        this.imgUrl = imgUrl;
        this.content = content;
        this.clapCount = 0;
        this.tag = tag;
        this.submitMissionId = submitMissionId;
        this.onHide = false;
    }

    public void update(Integer clapCount){
        this.clapCount = clapCount;
    }

    public void update(String content){
        if(content != null) this.content = content;
    }

    public void toggleFeedOnHide() {
        this.onHide = !getOnHide();
    }
}
