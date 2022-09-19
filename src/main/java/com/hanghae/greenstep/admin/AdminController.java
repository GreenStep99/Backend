package com.hanghae.greenstep.admin;


import com.hanghae.greenstep.shared.Message;
import com.hanghae.greenstep.shared.Status;
import com.hanghae.greenstep.submitMission.SubmitMissionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<?> adminLogin(@RequestBody AdminLoginRequestDto adminLoginRequestDto, HttpServletResponse response) {
        AdminLoginResponseDto adminLoginResponseDto = adminService.login(adminLoginRequestDto, response);
        return new ResponseEntity<>(Message.success(adminLoginResponseDto),HttpStatus.OK);
    }

    @GetMapping("/verification")
    public ResponseEntity<?> getSubmitMission(HttpServletRequest request){
        List<SubmitMissionResponseDto> submitMissionResponseDtoList = adminService.getSubmitMission(request);
        return new ResponseEntity<>(Message.success(submitMissionResponseDtoList), HttpStatus.OK);
    }

    @PostMapping("/verification/{submitMissionId}")
    public ResponseEntity<?> verifySubmitMission(@PathVariable Long submitMissionId, @RequestBody(required = false) Map<String, String> infoMap, @RequestParam Status verification, HttpServletRequest request){
        String info;
        if(infoMap == null) info = null;
        else info = infoMap.get("info");
        SubmitMissionResponseDto submitMissionResponseDto = adminService.verifySubmitMission(verification, submitMissionId, request, info);
        return new ResponseEntity<>(Message.success(submitMissionResponseDto),HttpStatus.OK);
    }

}
