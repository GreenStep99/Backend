package com.hanghae.greenstep.shared.notice;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@Embeddable
public class NotificationContent {
    private String content;

    public NotificationContent(String content) {
        this.content = content;
    }
}
