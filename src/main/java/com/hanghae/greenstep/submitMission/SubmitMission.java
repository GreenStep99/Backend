package com.hanghae.greenstep.submitMission;


import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.mission.Mission;
import com.hanghae.greenstep.shared.Status;
import com.hanghae.greenstep.shared.Timestamped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Audited
public class SubmitMission extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    private String imgUrl;

    @Column
    private String info;

    @Column
    private String adminName;

    @Column
    @NotAudited
    private Boolean onFeed;

    @Column
    @NotAudited
    private Boolean onHide;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @ManyToOne(fetch = FetchType.LAZY)
    private Mission mission;

    @Builder
    public SubmitMission(Status status, String imgUrl, Member member, Mission mission) {
        this.status = status;
        this.imgUrl = imgUrl;
        this.member = member;
        this.mission = mission;
        this.onFeed = false;
        this.onHide = false;
    }

    public void update(Status status,String info, String adminName){
        this.status = status;
        this.info = info;
        this.adminName = adminName;
    }

    public void toggleOnFeed() {
        this.onFeed = !getOnFeed();
    }

    public void toggleOnHide() {
        this.onHide = !getOnHide();
    }
}
