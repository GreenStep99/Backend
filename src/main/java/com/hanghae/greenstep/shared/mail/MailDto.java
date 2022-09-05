package com.hanghae.greenstep.shared.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MailDto {

    private String toAddress;
    private String title; // 제목
    private String content; // 메일 내용



}

