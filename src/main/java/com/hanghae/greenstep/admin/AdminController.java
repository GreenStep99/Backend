package com.hanghae.greenstep.admin;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    AdminService adminService;

    @GetMapping("/verification")
    public ResponseEntity<?> getSubmitMission(){
        return adminService.getSubmitMission();
    }
}
