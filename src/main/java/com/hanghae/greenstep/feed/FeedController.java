package com.hanghae.greenstep.feed;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @PostMapping("/profiles/missions/{submitMissionId}")
    public ResponseEntity<?> createFeed(@PathVariable Long submitMissionId, @RequestBody Map<String,String> contentMap, HttpServletRequest request){
        String content = contentMap.get("content");
        return feedService.createFeed(submitMissionId, content, request);
    }

    @GetMapping("/feed")
    public ResponseEntity<?> getFeed(@RequestParam Long lastFeedId, HttpServletRequest request){
        return feedService.getFeed(lastFeedId, request);
    }
    @GetMapping("/feed/tags/{tag}")
    public ResponseEntity<?> getFeedByTag(@PathVariable String tag, @RequestParam Long lastFeedId, HttpServletRequest request){
        return feedService.getFeedByTag(tag, lastFeedId, request);
    }

    @GetMapping("/profiles/feed")
    public ResponseEntity<?> getMyFeed(HttpServletRequest request){
        return feedService.getMyFeed(request);
    }

    @DeleteMapping("/feed")
    public ResponseEntity<?> deleteFeeds(@RequestBody List<Long> feedIdList, HttpServletRequest request){
        for(Long id:feedIdList){
        System.out.println(id);}
        return feedService.deleteFeeds(feedIdList, request);
    }

    @PatchMapping("/feed/{feedId}")
    public ResponseEntity<?> updateFeed(@PathVariable Long feedId,@RequestBody Map<String,String> contentMap, HttpServletRequest request){
        String content = contentMap.get("content");
        return feedService.updateFeed(feedId, content, request);
    }
}