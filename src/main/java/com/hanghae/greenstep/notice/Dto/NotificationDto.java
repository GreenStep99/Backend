package com.hanghae.greenstep.notice.Dto;

import com.hanghae.greenstep.notice.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class NotificationDto {
    private Long id;
    private String notificationContent;
    private String url;
    private String imgUrl;
    private Boolean status;
    private String createAt;

    public static NotificationDto create(Notification notification) {
        return new NotificationDto(notification.getId(), notification.getNotificationContent(),
                notification.getUrl(), notification.getImgUrl(), notification.getIsRead(), notification.getCreateAt());
    }

}
