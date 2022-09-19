package com.hanghae.greenstep.feed.Dto;

import com.hanghae.greenstep.feed.Feed;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FeedResponseDto {
    private Long id;
    private String missionName;
    private String missionImgUrl;
    private String content;
    private Boolean clapByMe;
    private Integer clapCount;
    private String tag;

    private String authorName;

    private String profilePhoto;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;


    public FeedResponseDto(Feed feed){
        this.id = feed.getId();
        this.missionName = feed.getMissionName();
        this.missionImgUrl = feed.getImgUrl();
        this.content = feed.getContent();
        this.clapCount = feed.getClapCount();
        this.tag = feed.getTag();
        this.createdAt =feed.getCreatedAt();
    }

}
