package com.hanghae.greenstep.kakaoShare.Dto;

import lombok.Getter;

@Getter
public class ContentDto {
    private String title;
    private String description;
    private String image_url;
    private int image_width;
    private int image_height;
    private LinkDto linkDto = new LinkDto();


    public ContentDto(String image_url, String content){
        this.title = "우리가 그린 푸른 발자국, 그린 스텝";
        this.description = content;
        this.image_url = image_url;
        this.image_height = 264;
        this.image_width = 130;
        this.linkDto = getLinkDto();


    }

}
