package com.repill.backend.discardrecord.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.repill.backend.apiPayload.ApiResponse;
import com.repill.backend.apiPayload.code.status.SuccessStatus;
import com.repill.backend.discardrecord.dto.DiscardRecordRequest;
import com.repill.backend.discardrecord.dto.DiscardRecordResponse;
import com.repill.backend.discardrecord.service.AzureBlobService;
import com.repill.backend.discardrecord.service.DiscardRecordService;
import com.repill.backend.global.security.handler.annotation.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/discard-records")
@Validated
@RequiredArgsConstructor
public class DiscardRecordController {

    private final DiscardRecordService discardRecordService;
    private final AzureBlobService azureBlobService;

    private final ObjectMapper objectMapper;

    @Operation(summary = "약 폐기하기 API", description = "이미지와 함께 약 폐기 기록을 생성합니다.")
    @PostMapping(consumes = {"multipart/form-data"})
    public ApiResponse<DiscardRecordResponse.DiscardRecordCreateResponse> createDiscardRecord(@AuthUser Long memberId,
                                                                                              @RequestPart("request") String request,
                                                                                              @RequestPart(value = "image", required = false) MultipartFile image) throws Exception {

        DiscardRecordRequest discardRecordRequest = objectMapper.readValue(request, DiscardRecordRequest.class);

        if (image != null && !image.isEmpty()) {
            String imageUrl = azureBlobService.uploadFile("re-pill-container", image);
        }

        DiscardRecordResponse.DiscardRecordCreateResponse response = discardRecordService.createDiscardRecord(memberId, discardRecordRequest);
        return ApiResponse.of(SuccessStatus._OK, response);
    }

    @Operation(summary = "약 폐기 기록 상세보기 API", description = "약을 폐기했던 기록의 상세정보를 조회합니다. 조회를 원하는 폐기 기록의 ID를 넘겨주세요")
    @GetMapping("/{recordId}")
    public ApiResponse<DiscardRecordResponse.DiscardRecordDetailResponse> getDiscardRecordDetail(@PathVariable Long recordId) {
        DiscardRecordResponse.DiscardRecordDetailResponse response = discardRecordService.getDiscardRecordDetail(recordId);
        return ApiResponse.of(SuccessStatus._OK, response);
    }

    @Operation(summary = "약 폐기 기록 리스트 보기 API", description = "약을 폐기했던 기록들의 리스트를 조회합니다.")
    @GetMapping
    public ApiResponse<DiscardRecordResponse.DiscardRecordListResponse> getDiscardRecordList(@AuthUser Long memberId) {
        DiscardRecordResponse.DiscardRecordListResponse response = discardRecordService.getDiscardRecordList(memberId);
        return ApiResponse.of(SuccessStatus._OK, response);
    }
}
