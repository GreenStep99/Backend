package com.hanghae.greenstep.kakaoShare.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SocialDto {

    private Integer clapCount;
    private Integer SharedCount;

    public SocialDto(Integer clapCount){
        this.clapCount = clapCount;
        this.SharedCount = getSharedCount();

    }
}
