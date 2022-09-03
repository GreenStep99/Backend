package com.hanghae.greenstep.feed;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @PostMapping("/profiles/missions/{postId}")
    public ResponseEntity<?> createFeed(@PathVariable Long postId, @RequestBody String content, HttpServletRequest request){
        return feedService.createFeed(postId, content, request);
    }

    @GetMapping("/feed")
    public ResponseEntity<?> getFeed(int lastFeedId, HttpServletRequest request){
        return feedService.getFeed(lastFeedId, request);
    }

}