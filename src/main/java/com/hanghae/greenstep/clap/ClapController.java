package com.hanghae.greenstep.clap;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class ClapController {
    private final ClapService clapService;

    @PostMapping("/profiles/missions/{postId}")
    public ResponseEntity<?> upDownClap(@PathVariable Long postId, HttpServletRequest request){
        return clapService.upDownClap(postId, request);
    }
}
