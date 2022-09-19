package com.hanghae.greenstep.admin.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminLoginRequestDto {
    private String email;
    private String password;
}
