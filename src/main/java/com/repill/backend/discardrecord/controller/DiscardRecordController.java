package com.repill.backend.discardrecord.controller;

import com.repill.backend.apiPayload.ApiResponse;
import com.repill.backend.apiPayload.code.status.SuccessStatus;
import com.repill.backend.discardrecord.dto.DiscardRecordRequest;
import com.repill.backend.discardrecord.dto.DiscardRecordResponse;
import com.repill.backend.discardrecord.service.DiscardRecordService;
import com.repill.backend.global.security.handler.annotation.AuthUser;
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
    public ApiResponse<DiscardRecordResponse.DiscardRecordCreateResponse> createDiscardRecord(@AuthUser Long memberId,
                                                                  @RequestBody @Validated DiscardRecordRequest request) {
        DiscardRecordResponse.DiscardRecordCreateResponse response = discardRecordService.createDiscardRecord(memberId, request);
        return ApiResponse.of(SuccessStatus._OK, response);
    }

    @GetMapping("/{recordId}")
    public ApiResponse<DiscardRecordResponse.DiscardRecordDetailResponse> getDiscardRecordDetail(@PathVariable Long recordId) {
        DiscardRecordResponse.DiscardRecordDetailResponse response = discardRecordService.getDiscardRecordDetail(recordId);
        return ApiResponse.of(SuccessStatus._OK, response);
    }
}
