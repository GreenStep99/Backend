package com.hanghae.greenstep.notice;

import com.hanghae.greenstep.notice.Dto.NotificationCountDto;
import com.hanghae.greenstep.notice.Dto.NotificationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class NotificationController {


    private final NotificationService notificationService;

    //SSE 구독
    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(HttpServletRequest request,
                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "")
                                String lastEventId) {


        return notificationService.subscribe(request, lastEventId);

    }

    //알림조회
    @GetMapping(value = "/notifications")
    public List<NotificationDto> findAllNotifications(HttpServletRequest request) {
        return notificationService.findAllNotifications(request);
    }

    //전체목록 알림 조회에서 해당 목록 클릭 시 읽음처리 ,
    @PostMapping("/notification/read/{notificationId}")
    public void readNotification(@PathVariable Long notificationId, HttpServletRequest request) {
        notificationService.readNotification(notificationId, request);

    }

    //알림 목록 접근 시 존재하는 목록들 전체 열람 확인처리
    @PostMapping("/notification/open")
    public void readNotification(HttpServletRequest request) {
        notificationService.openNotification(request);
    }

    //알림 조회 - 구독자가 현재 열어보지 않은 알림 갯수
    @GetMapping(value = "/notifications/count")
    public NotificationCountDto countUnReadNotifications(HttpServletRequest request) {
        return notificationService.countUnReadNotifications(request);
    }

    //알림 전체 삭제
    @DeleteMapping(value = "/notifications/delete/all")
    public ResponseEntity<Object> deleteNotificationAll(HttpServletRequest request) {

        return notificationService.deleteAllByNotifications(request);

    }

    //단일 알림 삭제
    @DeleteMapping(value = "/notifications/delete/{notificationId}")
    public ResponseEntity<Object> deleteNotification(@PathVariable Long notificationId) {

        return notificationService.deleteByNotification(notificationId);
    }

    //선택 알림 삭제
    @DeleteMapping(value = "/notifications/delete")
    public ResponseEntity<Object> deleteNotifications(@RequestBody Long[] notificationIdList) {

        return notificationService.deleteByNotifications(notificationIdList);
    }

}