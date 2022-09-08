package com.hanghae.greenstep.kakaoShare.Dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class KakaoTemplateDto {
    private final String objectType = "feed";

    private ContentDto content;

    private SocialDto social;

    private List<ButtonsDto> buttons;

    private List<String> friendIdList;

    @Builder
    public KakaoTemplateDto(ContentDto contentDto, SocialDto social, List<ButtonsDto> buttons, List<String> friendIdList){
        this.content = contentDto;
        this.social = social;
        this.buttons = buttons;
        this.friendIdList = friendIdList;
    }

}
