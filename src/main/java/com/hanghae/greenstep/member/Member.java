package com.hanghae.greenstep.member;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanghae.greenstep.clap.Clap;
import com.hanghae.greenstep.feed.Feed;
import com.hanghae.greenstep.jwt.RefreshToken;
import com.hanghae.greenstep.missionStatus.MissionStatus;
import com.hanghae.greenstep.post.Post;
import com.hanghae.greenstep.shared.Authority;
import com.hanghae.greenstep.submitMission.SubmitMission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@DynamicUpdate
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

    @Column(nullable = false)
    private String profilePhoto;

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Post> postList;

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Feed> feedList;
    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private Set<Clap> clapSet;
    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private Set<MissionStatus> missionStatusSet;
    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<SubmitMission> submitMissionList;

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    private RefreshToken refreshToken;

    @Builder
    public Member(Long id, String email,String name, Authority role, String nickname, String password, String profilePhoto) {
        this.id = getId();
        this.kakaoId = id;
        this.email = email;
        this.name = name;
        this.role = role;
        this.nickname = nickname;
        this.password = password;
        this.profilePhoto = profilePhoto;
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
}
