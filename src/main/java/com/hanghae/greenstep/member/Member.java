package com.hanghae.greenstep.member;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanghae.greenstep.clap.Clap;
import com.hanghae.greenstep.feed.Feed;
import com.hanghae.greenstep.jwt.RefreshToken;
import com.hanghae.greenstep.missionStatus.MissionStatus;
import com.hanghae.greenstep.shared.Authority;
import com.hanghae.greenstep.submitMission.SubmitMission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long kakaoId;

    @Column(unique = true)
    private String email;

    @Column(nullable = false,length = 60)
    private String name;

    @Column(nullable = false, length = 60)
    private String nickname;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Enumerated(value = EnumType.STRING)
    private Authority role;

    @Column
    private String type;

    @Column(nullable = false)
    private String profilePhoto;

    @Column
    private Long missionPoint;

    @Column
    private Long dailyMissionPoint;

    @Column
    private Long weeklyMissionPoint;

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Feed> feedList;
    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private Set<Clap> clapSet;
    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private Set<MissionStatus> missionStatusSet;
    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<SubmitMission> submitMissionList;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    private RefreshToken refreshToken;

    @Builder

    public Member(Long id, String email,String name, Authority role, String nickname, String password, String profilePhoto, String type) {
        this.id =getId();
        this.kakaoId = id;
        this.email = email;
        this.name = name;
        this.role = role;
        this.nickname = nickname;
        this.password = password;
        this.profilePhoto = profilePhoto;
        this.type =type;
        this.missionPoint = 0L;
        this.dailyMissionPoint = 0L;
        this.weeklyMissionPoint = 0L;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Member member = (Member) o;
        return id != null && Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


    public void update(MemberRequestDto memberRequestDto){
        if (memberRequestDto.getName() != null) this.name = memberRequestDto.getName();
        if (memberRequestDto.getNickname() != null) this.nickname = memberRequestDto.getNickname();
        if (memberRequestDto.getProfilePhoto() != null) this.profilePhoto = memberRequestDto.getProfilePhoto();
    }

    public void resetDailyPoint(){
        this.dailyMissionPoint = 0L;
    }

    public void resetWeeklyPoint(){
        this.weeklyMissionPoint = 0L;
    }

    public void earnDailyPoint(){
        this.missionPoint += 1L;
    }
    public void earnWeeklyPoint(){
        this.missionPoint += 2L;
    }
    public void earnChallengePoint(){
        this.missionPoint += 3L;
    }


}
