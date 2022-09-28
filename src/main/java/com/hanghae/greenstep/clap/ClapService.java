package com.hanghae.greenstep.clap;

import com.hanghae.greenstep.clap.Dto.ClapRequestDto;
import com.hanghae.greenstep.exception.CustomException;
import com.hanghae.greenstep.exception.ErrorCode;
import com.hanghae.greenstep.feed.Feed;
import com.hanghae.greenstep.feed.FeedRepository;
import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.notice.NotificationService;
import com.hanghae.greenstep.notice.NotificationType;
import com.hanghae.greenstep.shared.Check;
import lombok.RequiredArgsConstructor;
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
    public boolean toggleClap(Long feedId, HttpServletRequest request) {
        Member member = check.accessTokenCheck(request);
        Feed feed = feedRepository.findById(feedId).orElseThrow(
                () -> new CustomException(ErrorCode.POST_NOT_FOUND));

        Clap foundClap = clapRepository.findByMemberIdAndFeed(member.getId(),feed).orElse(null);

        if(foundClap == null){
            ClapRequestDto clapRequestDto = new ClapRequestDto(member, feed);
            Clap clap = new Clap(clapRequestDto);
            clapRepository.save(clap);
            Integer clapCount = clapRepository.countByFeed(feed);
            feed.update(clapCount);
            String Url = "https://greenstepapp.com/detailposts/"+feed.getId();
            //댓글 생성 시 모집글 작성 유저에게 실시간 알림 전송
            String content = member.getNickname()+"님이 당신의 그린스텝에 박수를 보냈어요!";
            String imgUrl = feed.getImgUrl();
            notificationService.send(clap.getFeed().getMember(), NotificationType.PRAISE, content, Url, imgUrl);
            return true;
        }
            clapRepository.deleteById(foundClap.getId());
            Integer clapCount = clapRepository.countByFeed(feed);
            feed.update(clapCount);
            return false;
    }
}
