package com.hanghae.greenstep.clap;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class ClapController {
    private final ClapService clapService;

    @PostMapping("/feed/claps/{feedId}")
    public ResponseEntity<?> toggleClap(@PathVariable Long feedId, HttpServletRequest request){
        return clapService.toggleClap(feedId, request);
    }
}
