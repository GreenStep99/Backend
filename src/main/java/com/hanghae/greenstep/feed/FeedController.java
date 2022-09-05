package com.hanghae.greenstep.feed;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @PostMapping("/profiles/missions/{submitMissionId}")
    public ResponseEntity<?> createFeed(@PathVariable Long submitMissionId, @RequestBody String content, HttpServletRequest request){
        return feedService.createFeed(submitMissionId, content, request);
    }

    @GetMapping("/feed")
    public ResponseEntity<?> getFeed(@RequestParam Long lastFeedId, HttpServletRequest request){
        return feedService.getFeed(lastFeedId, request);
    }
    @GetMapping("/feed/tags/{tag}")
    public ResponseEntity<?> getCategoriesFeed(@PathVariable String tag, @RequestParam Long lastFeedId, HttpServletRequest request){
        return feedService.getCategoriesFeed(tag, lastFeedId, request);
    }

    @GetMapping("/profiles/feed")
    public ResponseEntity<?> getMyFeed(HttpServletRequest request){
        return feedService.getMyFeed(request);
    }

    @DeleteMapping("/feed/{feedId}")
    public ResponseEntity<?> deleteFeed(@PathVariable Long feedId, HttpServletRequest request){
        return feedService.deleteFeed(feedId, request);
    }
    @PatchMapping("/feed/{feedId}")
    public ResponseEntity<?> updateFeed(@PathVariable Long feedId,@RequestBody String content, HttpServletRequest request){
        return feedService.updateFeed(feedId, content, request);
    }
}