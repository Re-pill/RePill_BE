package com.repill.backend.discardrecord.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class DiscardRecordResponse {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DiscardRecordCreateResponse {
        String message;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DiscardRecordDetailResponse {
        String name;
        LocalDate expirationDate;
        LocalDate discardedAt;
        String discardLocation;
        String imageUrl;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DiscardRecordListResponse {
        Integer totalCount;
        List<DiscardRecordDetailResponse> discardRecordDetailResponseList;
    }
}
