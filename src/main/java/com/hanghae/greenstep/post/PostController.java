package com.hanghae.greenstep.post;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/profiles/missions")
    public ResponseEntity<?> getMyPost(HttpServletRequest request){
        return postService.getMyPost(request);
    }
}
