package com.hanghae.greenstep.clap;

import com.hanghae.greenstep.exception.CustomException;
import com.hanghae.greenstep.exception.ErrorCode;
import com.hanghae.greenstep.feed.Feed;
import com.hanghae.greenstep.feed.FeedRepository;
import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.shared.Check;
import com.hanghae.greenstep.shared.Message;
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
    //n+1 문제 없음
    @Transactional
    public ResponseEntity<?> toggleClap(Long feedId, HttpServletRequest request) {
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
            return new ResponseEntity<>(Message.success(true), HttpStatus.OK);
        }
            clapRepository.deleteById(foundClap.getId());
            Integer clapCount = clapRepository.countByFeed(feed);
            feed.update(clapCount);
            return new ResponseEntity<>(Message.success(false), HttpStatus.OK);
    }
}
