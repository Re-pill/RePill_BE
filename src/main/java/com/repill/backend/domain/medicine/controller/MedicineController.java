package com.repill.backend.domain.medicine.controller;

import com.repill.backend.apiPayload.ApiResponse;
import com.repill.backend.apiPayload.code.status.SuccessStatus;
import com.repill.backend.global.security.handler.annotation.AuthUser;
import com.repill.backend.domain.medicine.dto.MedicineRequest;
import com.repill.backend.domain.medicine.dto.MedicineResponse;
import com.repill.backend.domain.medicine.service.MedicineService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/medicines")
@Validated
@RequiredArgsConstructor
public class MedicineController {

    private final MedicineService medicineService;

    @Operation(summary = "약품 등록 API", description = "약품 정보를 등록합니다.")
    @PostMapping
    public ApiResponse<MedicineResponse.MedicineDetailResponse> createMedicine(@AuthUser Long memberId,
                                                                               @RequestBody @Validated MedicineRequest request) {
        MedicineResponse.MedicineDetailResponse response = medicineService.createMedicine(memberId, request);
        return ApiResponse.of(SuccessStatus._OK, response);
    }

    @Operation(summary = "MY 약 D-Day 리스트 조회 API", description = "회원의 폐기되지 않은 약품 D-Day 리스트를 조회합니다.")
    @GetMapping("/d-day")
    public ApiResponse<MedicineResponse.MedicineDDayListResponse> getDDayList(@AuthUser Long memberId) {
        MedicineResponse.MedicineDDayListResponse response = medicineService.getDDayList(memberId);
        return ApiResponse.of(SuccessStatus._OK, response);
    }

    @Operation(summary = "약품 상세 조회 API", description = "약품의 상세 정보를 조회합니다.")
    @GetMapping("/{medicineId}")
    public ApiResponse<MedicineResponse.MedicineDetailResponse> getMedicineDetail(@PathVariable Long medicineId) {
        MedicineResponse.MedicineDetailResponse response = medicineService.getMedicineDetail(medicineId);
        return ApiResponse.of(SuccessStatus._OK, response);
    }

    @Operation(summary = "MY 약 삭제하기 API", description = "My약을 삭제합니다.")
    @DeleteMapping("/{medicineId}")
    public ApiResponse<String> deleteMedicine(@PathVariable Long medicineId, @AuthUser Long memberId) {
        medicineService.deleteMedicine(medicineId, memberId);
        return ApiResponse.of(SuccessStatus._OK,"MY약 삭제가 왼료되었습니다.");
    }

    @Operation(summary = "MY 약 수정하기 API", description = "My약 정보를 수정합니다.")
    @PatchMapping("/{medicineId}")
    public ApiResponse<String> patchMedicine(@PathVariable Long medicineId,
                                             @AuthUser Long memberId,
                                             @RequestBody MedicineRequest.patchMedicineRequest medicineRequest) {
        medicineService.patchMedicine(medicineId, memberId, medicineRequest);
        return ApiResponse.of(SuccessStatus._OK,"MY약 정보 수정이 완료되었습니다.");
    }
}
