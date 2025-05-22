package com.repill.backend.discardrecord.controller;

import com.repill.backend.apiPayload.ApiResponse;
import com.repill.backend.apiPayload.code.status.SuccessStatus;
import com.repill.backend.discardrecord.dto.DiscardRecordRequest;
import com.repill.backend.discardrecord.dto.DiscardRecordResponse;
import com.repill.backend.discardrecord.service.DiscardRecordService;
import com.repill.backend.global.security.handler.annotation.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/discard-records")
@Validated
@RequiredArgsConstructor
public class DiscardRecordController {

    private final DiscardRecordService discardRecordService;

    @PostMapping
    @Operation(summary = "약 폐기하기 API", description = "등록된 약을 올바르게 폐기하고 인증을 남깁니다.")
    public ApiResponse<DiscardRecordResponse.DiscardRecordCreateResponse> createDiscardRecord(@AuthUser Long memberId,
                                                                  @RequestBody @Validated DiscardRecordRequest request) {
        DiscardRecordResponse.DiscardRecordCreateResponse response = discardRecordService.createDiscardRecord(memberId, request);
        return ApiResponse.of(SuccessStatus._OK, response);
    }

    @GetMapping("/{recordId}")
    @Operation(summary = "약 폐기 기록 상세보기 API", description = "약을 폐기했던 기록의 상세정보를 조회합니다. 조회를 원하는 폐기 기록의 ID를 넘겨주세요")
    public ApiResponse<DiscardRecordResponse.DiscardRecordDetailResponse> getDiscardRecordDetail(@PathVariable Long recordId) {
        DiscardRecordResponse.DiscardRecordDetailResponse response = discardRecordService.getDiscardRecordDetail(recordId);
        return ApiResponse.of(SuccessStatus._OK, response);
    }

    @GetMapping
    @Operation(summary = "약 폐기 기록 리스트 보기 API", description = "약을 폐기했던 기록들의 리스트를 조회합니다.")
    public ApiResponse<DiscardRecordResponse.DiscardRecordListResponse> getDiscardRecordList(@AuthUser Long memberId) {
        DiscardRecordResponse.DiscardRecordListResponse response = discardRecordService.getDiscardRecordList(memberId);
        return ApiResponse.of(SuccessStatus._OK, response);
    }
}
