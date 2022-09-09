package com.hanghae.greenstep.submitMission;


import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.mission.Mission;
import com.hanghae.greenstep.shared.Status;
import com.hanghae.greenstep.shared.Timestamped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Audited
public class SubmitMission extends Timestamped {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(length = 80)
    private String missionName;

    @Column(length = 80)
    private String missionType;

    @Column
    private String imgUrl;

    @Column
    private String info;

    @Column
    private String adminName;

    @Column
    private Boolean onFeed;

    @Column
    private Boolean onHide;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @ManyToOne(fetch = FetchType.LAZY)
    private Mission mission;

    @Builder
    public SubmitMission(Status status, String imgUrl, Member member, Mission mission, String missionName, String missionType) {
        this.status = status;
        this.imgUrl = imgUrl;
        this.member = member;
        this.mission = mission;
        this.missionName = missionName;
        this.missionType = missionType;
        this.onFeed = false;
        this.onHide = false;
    }

    public void update(Status status,String info, String adminName){
        this.status = status;
        this.info = info;
        this.adminName = adminName;
    }

    public void makeOnFeed() {
        this.onFeed = true;
    }

    public void toggleOnHide() {
        if (getOnHide()) this.onHide = false;
        if (!getOnHide()) this.onHide = true;
    }
}
