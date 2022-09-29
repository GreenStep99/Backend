package com.hanghae.greenstep.shared;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CookieUtil {

    public void deleteAllCookies(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies(); // 모든 쿠키 가져오기
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName(); // 쿠키 이름 가져오기
                String value = cookie.getValue(); // 쿠키 값 가져오기
                log.info("쿠키 이름 : " + name + "   쿠키값 : " + value);
                cookie.setMaxAge(0); // 유효시간을 0으로 설정
                response.addCookie(cookie);
            }
        }
    }
}
