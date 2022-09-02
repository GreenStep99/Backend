package com.hanghae.greenstep.feed;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @PostMapping("/profiles/missions/{postId}")
    public ResponseEntity<?> createFeed(@PathVariable Long postId, @RequestBody String content, HttpServletRequest request){
        return feedService.createFeed(postId, content, request);
    }

}
