package com.repill.backend.member.controller;

import com.repill.backend.apiPayload.ApiResponse;
import com.repill.backend.apiPayload.code.status.SuccessStatus;
import com.repill.backend.global.security.handler.annotation.AuthUser;
import com.repill.backend.medicine.dto.MedicineRequest;
import com.repill.backend.medicine.dto.MedicineResponse;
import com.repill.backend.medicine.service.MedicineService;
import com.repill.backend.member.dto.MemberResponse;
import com.repill.backend.member.memberService.MemberService;
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

    @Operation(summary = "회원 정보 조회 API (마이페이지)",
            description = "현재 사용자의 회원 정보를 조회합니다.")
    @GetMapping
    public ApiResponse<MemberResponse.memberInfoResponse> createMedicine(@AuthUser Long memberId) {
        MemberResponse.memberInfoResponse result = memberService.getMemberInfo(memberId);
        return ApiResponse.onSuccess(result);
    }
}
