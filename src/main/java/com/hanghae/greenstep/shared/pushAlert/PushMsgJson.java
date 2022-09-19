package com.hanghae.greenstep.shared.pushAlert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PushMsgJson {
        private String collapse;
        private Integer timeToLive;
        private Boolean dryRun;
        private String priority;
        private CustomFieldDto customFieldDto;
        private PushContentDto notification;
        private String returnUrl;
        private String pushToken;
}

