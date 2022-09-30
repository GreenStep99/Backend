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

    // MIME TYPE - text/event-stream 형태로 받아야함. EventStream의 생성은 최초 클라이언트 요청으로 발생한다. EventStream이 생성되면 서버는 원하는 시점에 n개의 EventStream에 Event 데이터를 전송할 수 있다.
    // 클라이어트로부터 오는 알림 구독 요청을 받는다.
    // 로그인한 유저는 SSE 연결
    // lAST_EVENT_ID = 이전에 받지 못한 이벤트가 존재하는 경우 [ SSE 시간 만료 혹은 종료 ]
    // 전달받은 마지막 ID 값을 넘겨 그 이후의 데이터[ 받지 못한 데이터 ]부터 받을 수 있게 한다
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
    @DeleteMapping(value = "/notifications/delete")
    public ResponseEntity<Object> deleteNotifications(HttpServletRequest request) {

        return notificationService.deleteAllByNotifications(request);

    }

    //단일 알림 삭제
    @DeleteMapping(value = "/notifications/delete/{notificationId}")
    public ResponseEntity<Object> deleteNotification(@PathVariable Long notificationId) {

        return notificationService.deleteByNotifications(notificationId);
    }

}
    /*
        1. count -> 안 읽은 카운트
        2. reset -> 전체목록은 전체목록만 , 읽음처리를 할 수 있는 api가 필요함


        0. 구독 -> 서버로부터 오는 알람 받음

        2.notifications -> 내가 가지고있는 알림 목록을 다불러옴 [불러올 때 애들의 스테이터스 상태는 true가 됨]

        1.notifications/count -> notifications 알람에서 상태가 false 인 친구들을 가져옴
     */
