package com.hanghae.greenstep.post;

import com.hanghae.greenstep.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<PostResponseDto> findByMemberOrderByCreatedAtDesc(Member member);
}
