package com.hanghae.greenstep.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class LoginRequestDto {
    @NotBlank
    private String loginId;

    @NotBlank
    private String password;

    public LoginRequestDto(String loginId, String password){
        this.loginId = loginId;
        this.password =password;
    }
}