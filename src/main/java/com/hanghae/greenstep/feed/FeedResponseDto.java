package com.hanghae.greenstep.feed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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


    public FeedResponseDto(Feed feed){
        this.id = feed.getId();
        this.missionName = feed.getMissionName();
        this.missionImgUrl = feed.getImgUrl();
        this.content = feed.getContent();
        this.clapCount = feed.getClapCount();
        this.tag = feed.getTag();
    }

}
