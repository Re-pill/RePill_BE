package com.repill.backend.test;

import com.repill.backend.apiPayload.ApiResponse;
import com.repill.backend.global.security.handler.annotation.AuthUser;
import com.repill.backend.test.dto.TestResponse;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(
            summary = "테스트",
            description = "스웨거 사용 예시입니다")
    @GetMapping("/api-test")
    public ApiResponse<String> testOKAPI(){
        return ApiResponse.onSuccess("테스트");
    }

    @Operation(summary = "에러 핸들러 체크")
    @GetMapping("/exception")
    public ApiResponse<TestResponse.TempExceptionDTO> testexceptionAPI(@RequestParam("flag") Integer flag){
        testService.CheckFlag(flag);
        return ApiResponse.onSuccess(TestConverter.toTempExceptionDTO(flag));
    } // flag == 1 예외 발생

    @Operation(
            summary = "액세스 토큰에서 멤버 정보를 가져오는 예시",
            description = "현재 로그인 중인 사용자의 ID를 액세스 토큰에서 추출합니다.")
    @GetMapping("/get-memberInfo")
    public ApiResponse<String> getMemberTestAPI(@AuthUser Long memberId){
        String result = "현재 사용자 ID : " + (String.valueOf(memberId));
        return ApiResponse.onSuccess(result);
    }
}
