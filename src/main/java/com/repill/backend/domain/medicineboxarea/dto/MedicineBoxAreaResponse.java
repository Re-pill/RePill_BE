package com.repill.backend.domain.medicineboxarea.dto;

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
        Long id;
        String name;
        double latitude;
        double longitude;
        String address;
        String telephone;
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
