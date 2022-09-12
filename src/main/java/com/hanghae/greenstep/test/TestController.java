package com.hanghae.greenstep.test;

import com.hanghae.greenstep.shared.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test")
    public ResponseEntity<?> getTest(){
        return new ResponseEntity<>(Message.success("테스트"), HttpStatus.OK);
    }
}
