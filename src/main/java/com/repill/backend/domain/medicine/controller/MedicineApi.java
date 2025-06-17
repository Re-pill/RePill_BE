package com.repill.backend.domain.medicine.controller;

import com.repill.backend.apiPayload.ApiResponse;
import com.repill.backend.domain.medicine.dto.MedicineRequest;
import com.repill.backend.domain.medicine.dto.MedicineResponse;
import com.repill.backend.global.security.handler.annotation.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Medicine API", description = "약품 관련 API")
public interface MedicineApi {

    @Operation(summary = "약품 등록 API", description = "약품 정보를 등록합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "약품 등록 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "약품 등록 실패 - 잘못된 요청"),
    })
    @PostMapping
    ApiResponse<MedicineResponse.MedicineDetailResponse> createMedicine(@AuthUser Long memberId,
                                                                        @RequestBody @Validated MedicineRequest request);

    @Operation(summary = "MY 약 D-Day 리스트 조회 API", description = "회원의 폐기되지 않은 약품 D-Day 리스트를 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "D-Day 리스트 조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "회원의 약품이 존재하지 않음")
    })
    @GetMapping("/d-day")
    ApiResponse<MedicineResponse.MedicineDDayListResponse> getDDayList(@AuthUser Long memberId);

    @Operation(summary = "약품 상세 조회 API", description = "약품의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "약품 상세 조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "약품이 존재하지 않음")
    })
    @GetMapping("/{medicineId}")
    ApiResponse<MedicineResponse.MedicineDetailResponse> getMedicineDetail(@PathVariable Long medicineId);

    @Operation(summary = "MY 약 삭제하기 API", description = "My약을 삭제합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "약품 삭제 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "약품이 존재하지 않음")
    })
    @DeleteMapping("/{medicineId}")
    ApiResponse<String> deleteMedicine(@PathVariable Long medicineId, @AuthUser Long memberId);

    @Operation(summary = "MY 약 수정하기 API", description = "My약 정보를 수정합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "약품 수정 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "약품이 존재하지 않음")
    })
    @PatchMapping("/{medicineId}")
    ApiResponse<String> patchMedicine(@PathVariable Long medicineId,
                                             @AuthUser Long memberId,
                                             @RequestBody MedicineRequest.patchMedicineRequest medicineRequest);
}
