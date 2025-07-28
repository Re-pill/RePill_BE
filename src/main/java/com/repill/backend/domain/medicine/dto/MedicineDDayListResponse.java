package com.repill.backend.domain.medicine.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record MedicineDDayListResponse(

        Integer totalCount,
        List<MedicineDDayResponse> dDayResponseList
) {
}
