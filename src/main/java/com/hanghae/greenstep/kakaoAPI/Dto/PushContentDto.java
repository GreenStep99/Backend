package com.hanghae.greenstep.kakaoAPI.Dto;

import com.hanghae.greenstep.shared.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.hanghae.greenstep.shared.Status.DONE;

@Getter
@NoArgsConstructor
public class PushContentDto {
    private String title;
    private String content;

    public PushContentDto(Status status){
        if(status == DONE){
            this.title = "인증이 완료되었습니다.";
            this.content = "지금 바로 확인하세요!";
        } else {
            this.title = "인증이 실패하였습니다. ㅠㅠ";
            this.content = "자시 한번 도전해 주세요!";
        }
    }
}
