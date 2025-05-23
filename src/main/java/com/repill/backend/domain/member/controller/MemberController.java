package com.repill.backend.domain.member.controller;

import com.repill.backend.apiPayload.ApiResponse;
import com.repill.backend.global.security.handler.annotation.AuthUser;
import com.repill.backend.domain.member.dto.MemberResponse;
import com.repill.backend.domain.member.memberService.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@Validated
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원 정보 조회 API (마이페이지)", description = "현재 사용자의 회원 정보를 조회합니다.")
    @GetMapping
    public ApiResponse<MemberResponse.memberInfoResponse> createMedicine(@AuthUser Long memberId) {
        MemberResponse.memberInfoResponse result = memberService.getMemberInfo(memberId);
        return ApiResponse.onSuccess(result);
    }

    @Operation(summary = "폐기한 약품 종류 통계 (MY 페이지 하단 차트) API", description = "본인이 그동안 폐기했던 약품 종류의 통계를 조회합니다.")
    @GetMapping("/discard-medicines")
    public ApiResponse<MemberResponse.StatisticsByTypeOfDrug> getMedicineTypeStatistics(@AuthUser Long memberId) {
        MemberResponse.StatisticsByTypeOfDrug result = memberService.getMedicineTypeStatistics(memberId);
        return ApiResponse.onSuccess(result);
    }

    @Operation(summary = "나의 위치 등록 API", description = "사용자의 위치를 저장합니다.")
    @PostMapping("/api/members/location")
    public ApiResponse<String> saveLocation(@AuthUser Long memberId, String location) {
        memberService.saveLocation(memberId, location);
        return ApiResponse.onSuccess("위치 설정이 완료되었습니다.");
    }
}
