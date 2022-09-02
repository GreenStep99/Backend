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
    private Long postId;
    private String missionName;
    private String imgUrl;
    private String content;
    private Boolean clapByMe;
    private Integer clapCount;
}
