package com.hanghae.greenstep.member;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanghae.greenstep.feed.Feed;
import com.hanghae.greenstep.kakaoAPI.PushStatus;
import com.hanghae.greenstep.member.Dto.MemberRequestDto;
import com.hanghae.greenstep.shared.Authority;
import com.hanghae.greenstep.shared.Timestamped;
import com.hanghae.greenstep.submitMission.MissionStatus;
import com.hanghae.greenstep.submitMission.SubmitMission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.hanghae.greenstep.kakaoAPI.PushStatus.ALL;
import static com.hanghae.greenstep.kakaoAPI.PushStatus.APNS;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Member extends Timestamped {

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
    private Boolean acceptMail;

    @Column
    private PushStatus pushStatus;

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Feed> feedList;

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private Set<MissionStatus> missionStatusSet;

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<SubmitMission> submitMissionList;

    @Builder
    public Member(Long id, String email,String kakaoNickname, Authority role, String password, String profilePhoto, String type, Boolean acceptMail) {
        this.id =getId();
        this.kakaoId = id;
        this.email = email;
        this.name = kakaoNickname;
        this.role = role;
        this.nickname = "";
        this.password = password;
        this.profilePhoto = profilePhoto;
        this.type = type;
        this.acceptMail = acceptMail;
        this.pushStatus = ALL;
        this.missionPoint = 0L;
        this.dailyMissionPoint = 0L;
    }

    public Member(long l) {
        this.id = l;
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
        if (memberRequestDto.getAcceptMail() != null) this.acceptMail = memberRequestDto.getAcceptMail();
        if (memberRequestDto.getPushStatus()!=getPushStatus())this.pushStatus = memberRequestDto.getPushStatus();
    }

    public void resetDailyPoint(){
        this.dailyMissionPoint = 0L;
    }

    public void earnDailyPoint(){
        this.missionPoint += 10L;
        this.dailyMissionPoint += 10L;
    }
    public void earnWeeklyPoint(){
        this.missionPoint += 20L;
        this.dailyMissionPoint += 20L;
    }
    public void earnChallengePoint(){
        this.missionPoint += 30L;
        this.dailyMissionPoint += 30L;
    }

    public boolean validatePassword(PasswordEncoder passwordEncoder, String password){
        return passwordEncoder.matches(password,this.password);
    }

    public void deprecatePushSystem() {
        this.pushStatus = APNS;
    }

    public void updatePushStatus(PushStatus pushStatus) {
        this.pushStatus = pushStatus;
    }
}
