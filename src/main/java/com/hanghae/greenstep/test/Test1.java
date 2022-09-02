package com.hanghae.greenstep.test;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test1 {
    @GetMapping("/test")
    public String getSubmitMission(){
        return "test";
    }
}
