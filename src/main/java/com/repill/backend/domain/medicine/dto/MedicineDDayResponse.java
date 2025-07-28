package com.repill.backend.domain.medicine.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record MedicineDDayResponse(

        String name,
        LocalDate expirationDate,
        Integer dDay
) {
}
