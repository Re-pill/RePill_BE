package com.repill.backend.medicineboxarea.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class MedicineBoxAreaResponse {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MedicineBoxAreaDetailResponse {
        private Long id;
        private String name;
        private double latitude;
        private double longitude;
        private String address;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MedicineBoxAreaListResponse {
        Integer totalCount;
        List<MedicineBoxAreaDetailResponse> medicineBoxAreaDetailResponseList;
    }
}
