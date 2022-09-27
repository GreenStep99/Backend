package com.hanghae.greenstep.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //MEMBER
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "M001", "해당 유저를 찾을 수 없습니다."),
    TOKEN_IS_EXPIRED(HttpStatus.BAD_REQUEST.value(), "M002", "만료된 액세스 토큰 입니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST.value(), "M004", "유효하지 않은 토큰 입니다."),
    INVALID_MEMBER_INFO(HttpStatus.BAD_REQUEST.value(), "M005", "잘못된 사용자 정보입니다."),
    MEMBER_NOT_ALLOWED(HttpStatus.BAD_REQUEST.value(), "M006", "접근할 수 없는 페이지 입니다."),
    INVALID_INPUT(HttpStatus.BAD_REQUEST.value(), "M007", "잘못된 입력입니다."),

    // FEEDS & MY FEED
    POST_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "F001", "해당 게시물을 찾을 수 없습니다."),
    FEED_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "F002", "해당 게시물을 찾을 수 없습니다."),
    NOT_AUTHOR(HttpStatus.BAD_REQUEST.value(), "F003", "해당 게시물의 작성자가 아닙니다."),
    INVALID_VALUE(HttpStatus.BAD_REQUEST.value(), "F004", "잘못된 태그 정보입니다."),

    // MISSIONS
    MISSION_STATUS_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "MS001", "미션스테이터스를 찾을수 없습니다."),
    MISSION_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "MS002", "미션을 찾을수 없습니다."),


    // IMAGE
    FILE_TYPE_INVALID(HttpStatus.BAD_REQUEST.value(), "I001", "잘못된 파일 형식입니다."),
    FILE_SIZE_INVALID(HttpStatus.BAD_REQUEST.value(), "I002", "파일 크기가 너무 큽니다."),

    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "C001", "잘못된 요청입니다."),

    SHARE_FAILURE(HttpStatus.BAD_REQUEST.value(), "K001", "카카오톡 메세지 보내기에 실패했습니다."),
    
    //NOTIFICATION
    NOTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "N001", "해당 알림을 찾을 수 없습니다."),
    VALID_NOT_CONTENT(HttpStatus.BAD_REQUEST.value(), "N002", "유효하지 않는 내용 입니다."),
    FAIL_SUBSCRIBE(HttpStatus.NOT_FOUND.value(), "N003", "구독에 실패했습니다."),
    FAIL_LOAD_NOTIFICATION(HttpStatus.BAD_REQUEST.value(), "N004", "알림을 불러올 수 없습니다."),
    NOT_EXIST_NOTIFICATION(HttpStatus.NOT_FOUND.value(), "N005", "존재하지 않는 알림입니다."),
    FAIL_DELETE_All_NOTIFICATION(HttpStatus.BAD_REQUEST.value(), "N006", "전체 알림 삭제를 실패했습니다."),
    FAIL_DELETE_NOTIFICATION(HttpStatus.BAD_REQUEST.value(), "N007", "알림 삭제를 실패했습니다."),
    VALID_NOT_URL(HttpStatus.BAD_REQUEST.value(), "N008", "유효하지 않는 URL 입니다.");


    private final int httpStatus;
    private final String code;
    private final String message;


}
