package com.hanghae.greenstep.feed;

import com.hanghae.greenstep.clap.ClapRepository;
import com.hanghae.greenstep.exception.CustomException;
import com.hanghae.greenstep.exception.ErrorCode;
import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.shared.Check;
import com.hanghae.greenstep.shared.Message;
import com.hanghae.greenstep.submitMission.SubmitMission;
import com.hanghae.greenstep.submitMission.SubmitMissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final Check check;

    private final SubmitMissionRepository submitMissionRepository;
    private final FeedRepository feedRepository;
    private final ClapRepository clapRepository;

    public ResponseEntity<?> createFeed(Long submitMissionId, String content, HttpServletRequest request) {
        Member member = check.accessTokenCheck(request);
        SubmitMission submitMission = submitMissionRepository.findById(submitMissionId).orElseThrow(
                () -> new CustomException(ErrorCode.CARD_NOT_FOUND)
        );
        Feed feed = Feed.builder()
                .member(member)
                .missionName(submitMission.getMission().getMissionName())
                .content(content)
                .imgUrl(submitMission.getImgUrl())
                .tag(submitMission.getMission().getTag())
                .build();
        feedRepository.save(feed);
        return new ResponseEntity<>(Message.success(null), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> getFeed(Long lastFeedId, HttpServletRequest request) {
        Member member = check.accessTokenCheck(request);
        PageRequest pageRequest = PageRequest.of(0, 3);
        List<Feed> feedList = feedRepository.findByIdLessThanOrderByIdDesc(lastFeedId, pageRequest);
        List<FeedResponseDto> feedResponseDtoList = new ArrayList<>();
        for (Feed feed : feedList) {

            boolean clapByMe_isEdit = clapRepository.existsByMemberAndFeed(member, feed);

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

    @Transactional
    public ResponseEntity<?> getCategoriesFeed(String tag, Long lastFeedId, HttpServletRequest request) {
        Member member = check.accessTokenCheck(request);
        PageRequest pageRequest = PageRequest.of(0, 3);
        String tagName = switch (tag) {
            case "disposable" -> "#NO일회용품";
            case "separate" -> "#분리수거";
            case "environmental" -> "#환경운동";
            case "goods" -> "#환경용품사용";
            case "energy" -> "#에너지절약";
            case "etc" -> "#기타";
            default -> throw new CustomException(ErrorCode.INVALID_VALUE);
        };
        List<Feed> feedList = feedRepository.findByIdLessThanAndTagOrderByIdDesc(lastFeedId, tagName, pageRequest);
        List<FeedResponseDto> feedResponseDtoList = new ArrayList<>();
        for (Feed feed : feedList) {

            boolean clapByMe_isEdit = clapRepository.existsByMemberAndFeed(member, feed);

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