package com.repill.backend.medicine.controller;

import com.repill.backend.apiPayload.ApiResponse;
import com.repill.backend.apiPayload.code.status.SuccessStatus;
import com.repill.backend.medicine.dto.MedicineRequest;
import com.repill.backend.medicine.dto.MedicineResponse;
import com.repill.backend.medicine.service.MedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/medicines")
@Validated
@RequiredArgsConstructor
public class MedicineController {

    private final MedicineService medicineService;

    @PostMapping
    public ApiResponse<MedicineResponse.MedicineDetailResponse> createMedicine(@RequestBody @Validated MedicineRequest request) {
        MedicineResponse.MedicineDetailResponse response = medicineService.createMedicine(request);
        return ApiResponse.of(SuccessStatus._OK, response);
    }

    @GetMapping("/d-day/{memberId}")
    public ApiResponse<MedicineResponse.MedicineDDayListResponse> getDDayList(@PathVariable Long memberId) {
        MedicineResponse.MedicineDDayListResponse response = medicineService.getDDayList(memberId);
        return ApiResponse.of(SuccessStatus._OK, response);
    }
}
