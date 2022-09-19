package com.hanghae.greenstep.feed;

import com.hanghae.greenstep.feed.Dto.FeedContentDto;
import com.hanghae.greenstep.feed.Dto.FeedResponseDto;
import com.hanghae.greenstep.shared.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @PostMapping("/profiles/missions/{submitMissionId}")
    public ResponseEntity<?> createFeed(@PathVariable Long submitMissionId, @RequestBody FeedContentDto feedContentDto, HttpServletRequest request) {
        FeedResponseDto feedResponseDto = feedService.createFeed(submitMissionId, feedContentDto.getContent(), request);
        return new ResponseEntity<>(Message.success(feedResponseDto), HttpStatus.OK);
    }

    @GetMapping("/feed")
    public ResponseEntity<?> getFeed(@RequestParam Long lastFeedId, HttpServletRequest request){
        List<FeedResponseDto> feedResponseDtoList = feedService.getFeed(lastFeedId, request);
        return new ResponseEntity<>(Message.success(feedResponseDtoList), HttpStatus.OK);
    }
    @GetMapping("/feed/tags/{tag}")
    public ResponseEntity<?> getFeedByTag(@PathVariable String tag, @RequestParam Long lastFeedId, HttpServletRequest request){
        List<FeedResponseDto> feedResponseDtoList = feedService.getFeedByTag(tag, lastFeedId, request);
        return new ResponseEntity<>(Message.success(feedResponseDtoList), HttpStatus.OK);
    }

    @GetMapping("/profiles/feed")
    public ResponseEntity<?> getMyFeed(HttpServletRequest request){
        List<FeedResponseDto> feedResponseDtoList = feedService.getMyFeed(request);
        return new ResponseEntity<>(Message.success(feedResponseDtoList),HttpStatus.OK);
    }

    @DeleteMapping("/feed")
    public ResponseEntity<?> deleteFeeds(@RequestBody Long[] feedIdList, HttpServletRequest request){
        feedService.deleteFeeds(feedIdList, request);
        return new ResponseEntity<>(Message.success("삭제되었습니다."),HttpStatus.OK);
    }

    @PatchMapping("/feed/{feedId}")//
    public ResponseEntity<?> updateFeed(@PathVariable Long feedId, @RequestBody @Nullable FeedContentDto feedContentDto, HttpServletRequest request){
        FeedResponseDto feedResponseDto = feedService.updateFeed(feedId, Objects.requireNonNull(feedContentDto).getContent(), request);
        return new ResponseEntity<>(Message.success(feedResponseDto),HttpStatus.OK);
    }
    
}