package com.hanghae.greenstep.jwt;


import com.hanghae.greenstep.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.plaf.metal.MetalMenuBarUI;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

    Optional<RefreshToken> findByMember(Member member);

    void deleteByMember(Member member);
}
