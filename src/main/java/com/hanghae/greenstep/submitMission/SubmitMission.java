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

    @Column
    private String imgUrl;

    @Column
    private String reason;

    @Column
    private String adminName;

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
    }

    public SubmitMission(Status status, String imgUrl, String reason, String adminName, Member member, Mission mission) {
        this.id = getId();
        this.status = status;
        this.imgUrl = imgUrl;
        this.reason = reason;
        this.adminName = adminName;
        this.member = member;
        this.mission = mission;
    }

    public void update(Status status,String reason, String adminName){
        this.status = status;
        this.reason = reason;
        this.adminName = adminName;
    }
}
