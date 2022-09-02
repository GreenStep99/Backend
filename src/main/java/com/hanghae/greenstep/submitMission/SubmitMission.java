package com.hanghae.greenstep.submitMission;

import com.hanghae.greenstep.admin.SubmitMissionResponseDto;
import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.mission.Mission;
import com.hanghae.greenstep.post.Post;
import com.hanghae.greenstep.shared.Timestamped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class SubmitMission extends Timestamped {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Boolean status;

    @Column
    private String imgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Mission mission;

    @OneToOne(fetch = FetchType.LAZY)
    private Post post;

}
