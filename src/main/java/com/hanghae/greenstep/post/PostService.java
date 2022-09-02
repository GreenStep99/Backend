package com.hanghae.greenstep.post;

import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.shared.Check;
import com.hanghae.greenstep.shared.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final Check check;
    private final PostRepository postRepository;

    public ResponseEntity<?> getMyPost(HttpServletRequest request) {
        Member member = check.accessTokenCheck(request);
        List<Post> postList = postRepository.findByMemberOrderByCreatedAtDesc(member);
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        for(Post post:postList){
            PostResponseDto postResponseDto =new PostResponseDto(post);
            postResponseDtoList.add(postResponseDto);
        }
        return new ResponseEntity<>(Message.success(postResponseDtoList), HttpStatus.OK);
    }
}
