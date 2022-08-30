package com.hanghae.greenstep.member;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long kakaoId;

    @Column
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    private String profilePhoto;

    @Builder
    public Member(Long id, String email,String name, String nickname, String password, String profilePhoto) {
        this.kakaoId = id;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.profilePhoto = profilePhoto;
    }


    public boolean validatePassword(PasswordEncoder passwordEncoder, String password){
        return passwordEncoder.matches(password,this.password);
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
}
