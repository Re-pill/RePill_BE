package com.repill.backend.medicine.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
        Boolean isDiscarded;
        String medicineTypeName;
    }
}
