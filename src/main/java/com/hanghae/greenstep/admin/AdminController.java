package com.hanghae.greenstep.admin;


import com.hanghae.greenstep.shared.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;


    @PostMapping("/login")
    public ResponseEntity<?> adminLogin(@RequestBody AdminLoginRequestDto adminLoginRequestDto, HttpServletResponse response){
        return adminService.login(adminLoginRequestDto, response);
    }

    @GetMapping("/verification")
    public ResponseEntity<?> getSubmitMission(){
        return adminService.getSubmitMission();
    }

    @PostMapping("/verification/{submitMissionId}")
    public ResponseEntity<?> verifySubmitMission(@PathVariable Long submitMissionId, @RequestBody(required = false) Map<String, String> infoMap, @RequestParam Status verification, HttpServletRequest request){
        String info;
        if(infoMap == null) info = null;
        else info = infoMap.get("info");
        return adminService.verifySubmitMission(verification, submitMissionId, request, info);
    }

}
