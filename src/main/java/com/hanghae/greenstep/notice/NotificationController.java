package com.hanghae.greenstep.notice;


import com.hanghae.greenstep.jwt.UserDetailsImpl;
import com.hanghae.greenstep.shared.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    //참고한 블로그에 있던 swager 사용방식. 추후 이 방식으로 리팩토링 해보자. @ApiOperation(value = "알림 구독", notes = "알림을 구독한다.")
    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    @ResponseStatus(HttpStatus.OK)
    public SseEmitter subscribe(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        return notificationService.subscribe(userDetails.getMember().getId(), lastEventId);
    }

    //알림 조회
    @GetMapping(value = "/notifications")
    public List<NotificationResponseDto> findAllNotifications(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return notificationService.findAllNotifications(userDetails.getMember().getId());
    }

    //알림 목록에서 해당 목록 클릭 시 읽음처리 ,
    @PostMapping("/notification/read/{notificationId}")
    public void readNotification(@PathVariable Long notificationId){
        notificationService.readNotification(notificationId);
    }

    //읽지않은 알림 갯수 조회
    @GetMapping(value = "/notifications/count")
    public NotificationCountDto countUnReadNotifications(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return notificationService.countUnReadNotifications(userDetails.getMember().getId());
    }

    //알림 전체 삭제
    @DeleteMapping(value = "/notifications/delete")
    public ResponseEntity<Object> deleteNotifications(@AuthenticationPrincipal UserDetailsImpl userDetails){

        notificationService.deleteAllByNotifications(userDetails);
        return new ResponseEntity<>(Message.success("알림 목록 전체 삭제 성공"), HttpStatus.OK);
    }
    //단일 알림 삭제
    @DeleteMapping(value = "/notifications/delete/{notificationId}")
    public ResponseEntity<Object> deleteNotification(@PathVariable Long notificationId){

        notificationService.deleteByNotifications(notificationId);
        return new ResponseEntity<>(Message.success("알림 목록 삭제 성공"), HttpStatus.OK);
    }
}