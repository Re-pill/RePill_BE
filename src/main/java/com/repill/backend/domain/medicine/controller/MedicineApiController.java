package com.repill.backend.domain.medicine.controller;

import com.repill.backend.apiPayload.ApiResponse;
import com.repill.backend.apiPayload.code.status.SuccessStatus;
import com.repill.backend.domain.medicine.dto.MedicineRequest;
import com.repill.backend.domain.medicine.dto.MedicineResponse;
import com.repill.backend.domain.medicine.dto.PatchMedicineRequest;
import com.repill.backend.domain.medicine.service.MedicineService;
import com.repill.backend.global.security.handler.annotation.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/medicines")
@Validated
@RequiredArgsConstructor
public class
MedicineApiController implements MedicineApiDocs {

    private final MedicineService medicineService;

    @Override
    @PostMapping
    public ApiResponse<MedicineResponse.MedicineDetailResponse> createMedicine(@AuthUser Long memberId,
                                                                               @RequestBody @Validated MedicineRequest request) {
        MedicineResponse.MedicineDetailResponse response = medicineService.createMedicine(memberId, request);
        return ApiResponse.of(SuccessStatus._OK, response);
    }

    @Override
    @GetMapping("/d-day")
    public ApiResponse<MedicineResponse.MedicineDDayListResponse> getDDayList(@AuthUser Long memberId) {
        MedicineResponse.MedicineDDayListResponse response = medicineService.getDDayList(memberId);
        return ApiResponse.of(SuccessStatus._OK, response);
    }

    @Override
    @GetMapping("/{medicineId}")
    public ApiResponse<MedicineResponse.MedicineDetailResponse> getMedicineDetail(@PathVariable Long medicineId) {
        MedicineResponse.MedicineDetailResponse response = medicineService.getMedicineDetail(medicineId);
        return ApiResponse.of(SuccessStatus._OK, response);
    }

    @Override
    @DeleteMapping("/{medicineId}")
    public ApiResponse<String> deleteMedicine(@PathVariable Long medicineId, @AuthUser Long memberId) {
        medicineService.deleteMedicine(medicineId, memberId);
        return ApiResponse.of(SuccessStatus._OK,"MY약 삭제가 왼료되었습니다.");
    }

    @Override
    @PatchMapping("/{medicineId}")
    public ApiResponse<String> patchMedicine(@PathVariable Long medicineId,
                                             @AuthUser Long memberId,
                                             @RequestBody PatchMedicineRequest medicineRequest) {
        medicineService.patchMedicine(medicineId, memberId, medicineRequest);
        return ApiResponse.of(SuccessStatus._OK,"MY약 정보 수정이 완료되었습니다.");
    }
}
