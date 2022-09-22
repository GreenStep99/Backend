package com.hanghae.greenstep.notice.Dto;

import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.notice.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequestDto {
    private Member receiver;
    private NotificationType notificationType;
    private String notificationContent;
    private String url;
}
