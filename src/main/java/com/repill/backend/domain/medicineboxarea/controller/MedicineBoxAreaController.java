package com.repill.backend.domain.medicineboxarea.controller;

import com.repill.backend.apiPayload.ApiResponse;
import com.repill.backend.apiPayload.code.status.SuccessStatus;
import com.repill.backend.domain.medicineboxarea.dto.MedicineBoxAreaResponse;
import com.repill.backend.domain.medicineboxarea.service.MedicineBoxAreaService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/map")
@RequiredArgsConstructor
public class MedicineBoxAreaController {

    private final MedicineBoxAreaService medicineBoxAreaService;

    @Operation(summary = "폐의약품 수거함 위치 리스트 조회 API", description = "반경 3km 내의 폐의약품 수거함의 위치를 조회합니다.")
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

    @Operation(summary = "폐의약품 수거함 위치 리스트 조회 API", description = "폐의약품 수거함 유형의 따른 폐의약품 수거함의 위치를 조회합니다.")
    @GetMapping("/list/locationType")
    public ApiResponse<MedicineBoxAreaResponse.MedicineBoxAreaListResponse> getMapListByLocationType(@RequestParam double userLat,
                                                                                                     @RequestParam double userLng,
                                                                                                     @RequestParam String locationType) {

        List<MedicineBoxAreaResponse.MedicineBoxAreaDetailResponse> response = medicineBoxAreaService.getMedicineBoxAreaListByLocationType(userLat, userLng, locationType);

        return ApiResponse.of(SuccessStatus._OK, MedicineBoxAreaResponse.MedicineBoxAreaListResponse.builder()
                .totalCount(response.size())
                .medicineBoxAreaDetailResponseList(response)
                .build());
    }

    @Operation(summary = "폐의약품 수거함 상세 조회 API", description = "넘겨받은 ID에 해당하는 폐의약품 수거함을 상세 조회합니다.")
    @GetMapping("/{medicineBoxAreaId}")
    public ApiResponse<MedicineBoxAreaResponse.MedicineBoxAreaDetailResponse> getMedicineBoxAreaDetail(@PathVariable Long medicineBoxAreaId) {
        MedicineBoxAreaResponse.MedicineBoxAreaDetailResponse result = medicineBoxAreaService.getMedicineBoxAreaDetail(medicineBoxAreaId);
        return ApiResponse.of(SuccessStatus._OK, result);
    }
}
