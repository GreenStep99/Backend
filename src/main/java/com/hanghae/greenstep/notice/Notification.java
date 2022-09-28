package com.hanghae.greenstep.notice;

import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.shared.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@AllArgsConstructor
@Getter
@Entity
@NoArgsConstructor
public class Notification extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @Embedded
    private NotificationContent notificationContent;
    //알림내용 - 50자 이내

    @Embedded
    private RelatedURL url;
    //관련 url - 클릭시 이동해야할 링크

    @Embedded
    private RelatedIMGURL imgUrl;
    //관련 url - 이미지 url

    @Column(nullable = false)
    private Boolean isRead;
    //읽었는지에 대한 여부

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType notificationType;
    // 알림 종류 [신청 / 수락 / 거절 등등 ]

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action= OnDeleteAction.CASCADE)
    @JoinColumn(name = "member_id")
    private Member receiver;
    //회원정보
    @Builder
    public Notification(Member receiver, NotificationType notificationType, String notificationContent, String url, String imgUrl, Boolean isRead) {
        this.receiver = receiver;
        this.notificationType = notificationType;
        this.notificationContent = new NotificationContent(notificationContent);
        this.url = new RelatedURL(url);
        this.imgUrl = new RelatedIMGURL(imgUrl);
        this.isRead = isRead;
    }

    public void read() {
        isRead = true;
    }

    public String getNotificationContent() {
        return notificationContent.getNotificationContent();
    }

    public String getUrl() {
        return url.getUrl();
    }

    public String getImgUrl() {
        return imgUrl.getImgUrl();
    }

}

/*
 알림 기능 처리 요소
  - 누구 : ~ 에 대한 알림이 도착했다. 형식의 알림을 클릭하면 관련 페이지로 이동하는 방식.
  - 알림을 읽으면 '읽음' 처리가 되어야한다.

 */
