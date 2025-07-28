package com.repill.backend.domain.medicine.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record MedicineDetailResponse(

        Long medicineId,
        String name,
        Integer count,
        LocalDate expirationDate,
        Boolean discarded,
        LocalDate discardedAt,
        String discardLocation,
        String medicineTypeName
) {
}
