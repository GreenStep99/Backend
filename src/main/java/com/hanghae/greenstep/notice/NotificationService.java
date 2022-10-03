package com.hanghae.greenstep.notice;

import com.hanghae.greenstep.exception.CustomException;
import com.hanghae.greenstep.exception.ErrorCode;
import com.hanghae.greenstep.feed.Dto.FeedResponseDto;
import com.hanghae.greenstep.feed.Feed;
import com.hanghae.greenstep.member.Member;
import com.hanghae.greenstep.notice.Dto.NotificationCountDto;
import com.hanghae.greenstep.notice.Dto.NotificationDto;
import com.hanghae.greenstep.shared.Check;
import com.hanghae.greenstep.shared.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
    private final EmitterRepository emitterRepository = new EmitterRepositoryImpl();

    private final NotificationRepository notificationRepository;
    private final Check check;

    public SseEmitter subscribe(HttpServletRequest request, String lastEventId) {
        Member member = check.accessTokenCheck(request);
        Long userId = member.getId();

        String emitterId = makeTimeIncludeId(userId);

        Long timeout = 60L * 1000L * 60L; // 1시간
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(timeout));
        try {
            emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
            emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

            String eventId = makeTimeIncludeId(userId);
            sendNotification(emitter, eventId, emitterId, "EventStream Created. [userId=" + userId + "]");

            if (hasLostData(lastEventId)) {
                sendLostData(lastEventId, userId, emitterId, emitter);
            }
        }catch (Exception e){
            log.info("다다다다다다다다다다");
            throw new CustomException(ErrorCode.FAIL_SUBSCRIBE);
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
        Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartWithByUserId(String.valueOf(userId));
        eventCaches.entrySet().stream()
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                .forEach(entry -> sendNotification(emitter, entry.getKey(), emitterId, entry.getValue()));
    }

    @Async
    public void send(Member receiver, NotificationType notificationType, String notificationContent, String url, String imgUrl) {

        Notification notification = notificationRepository.save(createNotification(receiver, notificationType, notificationContent, url, imgUrl));

        String receiverId = String.valueOf(receiver.getId());
        String eventId = receiverId + "_" + System.currentTimeMillis();
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByUserId(receiverId);
        emitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, notification);
                    sendNotification(emitter, eventId, key, NotificationDto.create(notification));
                }
        );


    }
    private Notification createNotification(Member receiver, NotificationType notificationType, String notificationContent, String url, String imgUrl) {
        return Notification.builder()
                .receiver(receiver)
                .notificationType(notificationType)
                .notificationContent(notificationContent)
                .url(url)
                .imgUrl(imgUrl)
                .isRead(false)
                .isOpen(false)
                .build();
    }

    @Transactional
    public List<NotificationDto> findAllNotifications(HttpServletRequest request) {

        Member member = check.accessTokenCheck(request);
        Long userId = member.getId();
        List<Notification> notifications = notificationRepository.findAllByUserId(userId);
        try {
            return notifications.stream()
                    .map(NotificationDto::create)
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw  new CustomException(ErrorCode.FAIL_LOAD_NOTIFICATION);
        }finally {
            if (notifications.stream()!=null){
                notifications.stream().close();
            }
        }

    }


    public NotificationCountDto countUnReadNotifications(HttpServletRequest request) {
        Member member = check.accessTokenCheck(request);
        Long userId = member.getId();
        Long count = notificationRepository.countUnOpenNotifications(userId);
        return NotificationCountDto.builder()
                .count(count)
                .build();

    }

    @Transactional
    public List<NotificationDto> readNotification(Long notificationId, HttpServletRequest request) {
        Optional<Notification> notification = notificationRepository.findById(notificationId);
        Notification checkNotification = notification.orElseThrow(()-> new CustomException(ErrorCode.NOT_EXIST_NOTIFICATION));
        checkNotification.read();
        return findAllNotifications(request);

    }

    @Transactional
    public List<NotificationDto> openNotification(HttpServletRequest request) {
        Member member = check.accessTokenCheck(request);
        Long receiverId = member.getId();
        List<Notification> notificationList = notificationRepository.findAllByUserId(receiverId);
        for(Notification notification : notificationList){
            notification.open();
        }
        return findAllNotifications(request);

    }

    @Transactional
    public ResponseEntity<Object> deleteAllByNotifications(HttpServletRequest request) {
        Member member = check.accessTokenCheck(request);
        Long receiverId = member.getId();
        try {
            notificationRepository.deleteAllByReceiverId(receiverId);
            return new ResponseEntity<>(Message.success("알림 목록 전체 삭제 성공"),HttpStatus.OK);
        }catch (Exception e){
                throw new CustomException(ErrorCode.FAIL_DELETE_All_NOTIFICATION);
        }

    }
    @Transactional
    public ResponseEntity<Object> deleteByNotification(Long notificationId) {
        try{
            Optional<Notification> notification = notificationRepository.findById(notificationId);
            if(notification.isPresent()){
                notificationRepository.deleteById(notificationId);
                return new ResponseEntity<>(Message.success("알림 목록 삭제 성공"),HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Message.success("존재하지 않는 알림입니다."),HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw new CustomException(ErrorCode.FAIL_DELETE_NOTIFICATION);
        }
    }

    @Transactional
    public ResponseEntity<Object> deleteByNotifications(Long[] notificationIdList) {
        for(Long notificationId : notificationIdList) {
            Notification notification = notificationRepository.findById(notificationId).orElseThrow(
                    () -> new CustomException(ErrorCode.NOT_EXIST_NOTIFICATION)
            );
            notificationRepository.delete(notification);
        }
        return new ResponseEntity<>(Message.success("알림 목록 삭제 성공"),HttpStatus.OK);

    }
}
