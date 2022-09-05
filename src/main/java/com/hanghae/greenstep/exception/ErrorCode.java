package com.hanghae.greenstep.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //MEMBER
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "M001", "해당 유저를 찾을 수 없습니다."),
    NOT_AUTHOR(HttpStatus.BAD_REQUEST.value(), "M002", "작성자가 아닙니다."),
    TOKEN_IS_EXPIRED(HttpStatus.BAD_REQUEST.value(), "M003", "만료된 액세스 토큰 입니다."),
    REFRESH_TOKEN_IS_EXPIRED(HttpStatus.BAD_REQUEST.value(), "M004", "만료된 리프레시 토큰 입니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST.value(), "M005", "유효하지 않은 토큰 입니다."),
    DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST.value(), "M006", "이미 사용되고 있는 이메일입니다."),
    INVALID_MEMBER_INFO(HttpStatus.BAD_REQUEST.value(), "M007", "잘못된 사용자 정보입니다."),
    INVALID_VALUE(HttpStatus.BAD_REQUEST.value(), "M008", "잘못된 태그 정보입니다."),


    POST_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "P001", "해당 게시물을 찾을 수 없습니다."),
    FEED_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "F001", "해당 게시물을 찾을 수 없습니다."),
    BAD_REQUEST(HttpStatus.NOT_FOUND.value(), "F002", "해당 게시물의 작성자가 아닙니다."),

    // IMAGE
    FILE_TYPE_INVALID(HttpStatus.BAD_REQUEST.value(), "I001", "잘못된 파일 형식입니다."),
    FILE_SIZE_INVALID(HttpStatus.BAD_REQUEST.value(), "I002", "파일 크기가 너무 큽니다."),

    // EMAIL
    SEND_EMAIL_FAILURE(HttpStatus.BAD_REQUEST.value(), "E001", "메일 전송에 실패하였습니다");


 private final int httpStatus;
 private final String code;
 private final String message;


}
