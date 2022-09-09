package com.hanghae.greenstep.kakaoShare.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class KakaoTemplateDto {
    private final String objectType = "feed";

    private ContentDto content;

    private SocialDto social;

    private List<ButtonsDto> buttons;

    private String[] friendIdList;

    @Builder
    public KakaoTemplateDto(ContentDto contentDto, SocialDto social, List<ButtonsDto> buttons, String[] friendIdList){
        this.content = contentDto;
        this.social = social;
        this.buttons = buttons;
        this.friendIdList = friendIdList;
    }

}
