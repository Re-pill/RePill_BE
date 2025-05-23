package com.repill.backend.medicineboxarea.controller;

import com.repill.backend.apiPayload.ApiResponse;
import com.repill.backend.apiPayload.code.status.SuccessStatus;
import com.repill.backend.medicineboxarea.dto.MedicineBoxAreaResponse;
import com.repill.backend.medicineboxarea.service.MedicineBoxAreaService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/map")
@RequiredArgsConstructor
public class MedicineBoxAreaController {

    private final MedicineBoxAreaService medicineBoxAreaService;

    @Operation(summary = "폐의약품 수거함 위치 리스트 조회 API", description = "폐의약품 수거함의 위치를 조회합니다.")
    @GetMapping("/list")
    public ApiResponse<MedicineBoxAreaResponse.MedicineBoxAreaListResponse> getMapList(@RequestParam double userLat,
                                                                                       @RequestParam double userLng,
                                                                                       @RequestParam String filter) {

        List<MedicineBoxAreaResponse.MedicineBoxAreaDetailResponse> response = medicineBoxAreaService.getMedicineBoxAreaList(userLat, userLng, filter);

        return ApiResponse.of(SuccessStatus._OK, MedicineBoxAreaResponse.MedicineBoxAreaListResponse.builder()
                        .totalCount(response.size())
                        .medicineBoxAreaDetailResponseList(response)
                        .build());
    }
}
