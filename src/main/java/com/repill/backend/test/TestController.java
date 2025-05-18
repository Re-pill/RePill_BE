package com.repill.backend.test;

import com.repill.backend.apiPayload.ApiResponse;
import com.repill.backend.test.dto.TestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping("/api-test")
    public ApiResponse<String> testOKAPI(){
        return ApiResponse.onSuccess("테스트");
    }

    @GetMapping("/exception")
    public ApiResponse<TestResponse.TempExceptionDTO> testexceptionAPI(@RequestParam("flag") Integer flag){
        testService.CheckFlag(flag);
        return ApiResponse.onSuccess(TestConverter.toTempExceptionDTO(flag));
    } // flag == 1 예외 발생
}
