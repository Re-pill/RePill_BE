package com.repill.backend.domain.medicine.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class MedicineResponse {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MedicineDetailResponse {
        Long medicineId;
        String name;
        Integer count;
        LocalDate expirationDate;
        Boolean discarded;
        LocalDate discardedAt;
        String discardLocation;
        String medicineTypeName;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MedicineDDayResponse {
        String name;
        LocalDate expirationDate;
        Integer dDay;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MedicineDDayListResponse {
        Integer totalCount;
        List<MedicineDDayResponse> dDayResponseList;
    }
}
