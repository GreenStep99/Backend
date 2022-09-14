package com.hanghae.greenstep.clap;

import com.hanghae.greenstep.exception.CustomException;
import com.hanghae.greenstep.exception.ErrorCode;
import com.hanghae.greenstep.feed.Feed;
import com.hanghae.greenstep.feed.FeedRepository;
import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.notice.NotificationService;
import com.hanghae.greenstep.shared.Check;
import com.hanghae.greenstep.shared.Message;
import com.hanghae.greenstep.shared.notice.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Service
public class ClapService {
    private final Check check;
    private final FeedRepository feedRepository;
    private final ClapRepository clapRepository;
    private final NotificationService notificationService;
    //n+1 문제 없음
    @Transactional
    public ResponseEntity<?> toggleClap(Long feedId, HttpServletRequest request) {
        Member member = check.accessTokenCheck(request);
        Feed feed = feedRepository.findById(feedId).orElseThrow(
                () -> new CustomException(ErrorCode.POST_NOT_FOUND));

        Clap foundClap = clapRepository.findByMemberAndFeed(member,feed).orElse(null);

        if(foundClap == null){
            ClapRequestDto clapRequestDto = new ClapRequestDto(member, feed);
            Clap clap = new Clap(clapRequestDto);
            clapRepository.save(clap);
            Integer clapCount = clapRepository.countByFeed(feed);
            feed.update(clapCount);
            String Url = "https://www.greenstepapp.com/feed";
            //댓글 생성 시 모집글 작성 유저에게 실시간 알림 전송 ,
            String content = clap.getMember().getNickname()+"님! 미션 인증이 완료되었습니다!";
            notificationService.send(clap.getMember(), NotificationType.PRAISE, content, Url);
            return new ResponseEntity<>(Message.success(true), HttpStatus.OK);
        }
            clapRepository.deleteById(foundClap.getId());
            Integer clapCount = clapRepository.countByFeed(feed);
            feed.update(clapCount);
            return new ResponseEntity<>(Message.success(false), HttpStatus.OK);
    }
}
