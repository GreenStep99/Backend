package com.hanghae.greenstep.kakaoAPI.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PushMsgDto {
        private Boolean dryRun;
        private CustomFieldDto customFieldDto;
        private PushContentDto notification;



        @Builder
        public PushMsgDto(Boolean dryRun, CustomFieldDto customFieldDto, PushContentDto pushContentDto) {
                this.dryRun = dryRun;
                this.customFieldDto = customFieldDto;
                this.notification = pushContentDto;
        }

}

