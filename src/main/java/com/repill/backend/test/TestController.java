package com.repill.backend.test;

import com.repill.backend.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/ok")
    public ApiResponse<String> testAPI(){
        return ApiResponse.onSuccess("테스트");
    }
}
