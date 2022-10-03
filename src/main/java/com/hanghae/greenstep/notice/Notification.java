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

    @Embedded
    private RelatedURL url;

    @Embedded
    private RelatedIMGURL imgUrl;

    @Column(nullable = false)
    private Boolean isRead;

    @Column(nullable = false)
    private Boolean isOpen;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType notificationType;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action= OnDeleteAction.CASCADE)
    @JoinColumn(name = "member_id")
    private Member receiver;

    @Builder
    public Notification(Member receiver, NotificationType notificationType, String notificationContent, String url, String imgUrl, Boolean isRead, Boolean isOpen) {
        this.receiver = receiver;
        this.notificationType = notificationType;
        this.notificationContent = new NotificationContent(notificationContent);
        this.url = new RelatedURL(url);
        this.imgUrl = new RelatedIMGURL(imgUrl);
        this.isRead = isRead;
        this.isOpen = isOpen;
    }

    public void read() {
        isRead = true;
    }
    public void open() {
        isOpen = true;
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

    public String getCreateAt(){
        return String.valueOf(getCreatedAt());
    }


}