package com.hanghae.greenstep.jwt;

import com.hanghae.greenstep.admin.Admin;
import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.shared.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;


@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken extends Timestamped {

    @Id
    @Column(nullable = false)
    private Long id;

    @JoinColumn(name = "members_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "admin_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Admin admin;


    @Column(name = "token_value", nullable = false)
    private String value;

    public void updateValue(String token){
        this.value = token;
    }

}
