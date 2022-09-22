package com.hanghae.greenstep.notice;

import com.hanghae.greenstep.exception.CustomException;
import com.hanghae.greenstep.exception.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor

public class NotificationContent {
    private static final int Max_LENGTH = 50;

    @Column(nullable = false, length = Max_LENGTH)
    private String notificationContent;


    public NotificationContent(String notificationContent) {
        if (isNotValidNotificationContent(notificationContent)) {
            throw new CustomException(ErrorCode.VALID_NOT_CONTENT);
        }
        this.notificationContent = notificationContent;
    }

    private boolean isNotValidNotificationContent(String notificationContent) {
        return Objects.isNull(notificationContent) || notificationContent.length() > Max_LENGTH
                || notificationContent.isEmpty();

    }
}
