package com.hanghae.greenstep.notice;

import com.hanghae.greenstep.exception.CustomException;
import com.hanghae.greenstep.exception.ErrorCode;
import com.hanghae.greenstep.jwt.UserDetailsImpl;
import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.notice.Dto.NotificationCountDto;
import com.hanghae.greenstep.notice.Dto.NotificationResponseDto;
import com.hanghae.greenstep.shared.notice.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final EmitterRepository emitterRepository = new EmitterRepositoryImpl();
    private final NotificationRepository notificationRepository;

    public SseEmitter subscribe(Long userId, String lastEventId) {
        String emitterId = makeTimeIncludeId(userId);

        Long timeout = 60L * 1000L * 60L;
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(timeout));

        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        String eventId = makeTimeIncludeId(userId);
        sendNotification(emitter, eventId, emitterId, "EventStream Created. [userId=" + userId + "]");

        if (hasLostData(lastEventId)) {
            sendLostData(lastEventId, userId, emitterId, emitter);
        }
        return emitter;
    }

    private String makeTimeIncludeId(Long userId) {
        return userId + "_" + System.currentTimeMillis();
    }

    private void sendNotification(SseEmitter emitter, String eventId, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(emitterId);
        }
    }


    private boolean hasLostData(String lastEventId) {
        return !lastEventId.isEmpty();
    }
    private void sendLostData(String lastEventId, Long userId, String emitterId, SseEmitter emitter) {
        Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartWithByMemberId(String.valueOf(userId));
        eventCaches.entrySet().stream()
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                .forEach(entry -> sendNotification(emitter, entry.getKey(), emitterId, entry.getValue()));
    }

    @Async
    public void send(Member receiver, NotificationType notificationType, String content, String url) {

        Notification notification = notificationRepository.save(createNotification(receiver, notificationType, content, url));

        String receiverId = String.valueOf(receiver.getId());
        String eventId = receiverId + "_" + System.currentTimeMillis();
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByMemberId(receiverId);
        emitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, notification);
                    sendNotification(emitter, eventId, key, NotificationResponseDto.create(notification));
                }
        );


    }

    private Notification createNotification(Member receiver, NotificationType notificationType, String content, String url) {
        return Notification.builder()
                .receiver(receiver)
                .notificationType(notificationType)
                .content(content)
                .url(url)
                .isRead(false)
                .build();
    }

    @Transactional
    public List<NotificationResponseDto> findAllNotifications(Long userId) {
        List<Notification> notifications = notificationRepository.findAllByUserId(userId);
        return notifications.stream()
                .map(NotificationResponseDto::create)
                .collect(Collectors.toList());
    }

    @Transactional
    public void readNotification(Long notificationId) {
        Optional<Notification> notification = notificationRepository.findById(notificationId);
        Notification checkNotification = notification.orElseThrow(()-> new CustomException(ErrorCode.NOTIFICATION_NOT_FOUND));
        checkNotification.read();

    }

    public NotificationCountDto countUnReadNotifications(Long userId) {
        Long count = notificationRepository.countUnReadNotifications(userId);
        return NotificationCountDto.builder()
                .count(count)
                .build();
    }

    @Transactional
    public void deleteAllByNotifications(UserDetailsImpl userDetails) {
        Long receiverId = userDetails.getMember().getId();
        notificationRepository.deleteAllByReceiverId(receiverId);

    }
    @Transactional
    public void deleteByNotifications(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }

}
