package com.hanghae.greenstep.admin;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminLoginRequestDto {

    private String username;
    private String password;
}
