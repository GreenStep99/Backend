package com.hanghae.greenstep.feed;

import com.hanghae.greenstep.clap.ClapRepository;
import com.hanghae.greenstep.exception.CustomException;
import com.hanghae.greenstep.exception.ErrorCode;
import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.post.Post;
import com.hanghae.greenstep.post.PostRepository;
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
public class FeedService {

    private final Check check;
    private final PostRepository postRepository;
    private final FeedRepository feedRepository;
    private final ClapRepository clapRepository;

    public ResponseEntity<?> createFeed(Long postId, String content, HttpServletRequest request) {
        Member member = check.accessTokenCheck(request);
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(ErrorCode.CARD_NOT_FOUND)
        );
        Feed feed = Feed.builder()
                .member(member)
                .missionName(post.getMissionName())
                .content(content)
                .imgUrl(post.getImgUrl())
                .build();
        feedRepository.save(feed);
        return new ResponseEntity<>(Message.success(null), HttpStatus.OK);
    }

    public ResponseEntity<?> getFeed(HttpServletRequest request) {
        Member member = check.accessTokenCheck(request);
        List<Feed> feedList = feedRepository.findAllByOrderByCreatedAtDesc();
        List<FeedResponseDto> feedResponseDtoList = new ArrayList<>();
        for (Feed feed : feedList) {
            boolean clapByMe_isEdit = clapRepository.findByMemberAndFeed(member, feed);

            feedResponseDtoList.add(
                    FeedResponseDto.builder()
                            .postId(feed.getId())
                            .missionName(feed.getMissionName())
                            .imgUrl(feed.getImgUrl())
                            .content(feed.getContent())
                            .clapByMe(clapByMe_isEdit)
                            .clapCount(feed.getClapCount())
                            .build()
            );
        }
        return new ResponseEntity<>(Message.success(feedResponseDtoList), HttpStatus.OK);
    }
}