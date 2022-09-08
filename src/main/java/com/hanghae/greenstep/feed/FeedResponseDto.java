package com.hanghae.greenstep.feed;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
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
