package com.repill.backend.discardrecord.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscardRecordRequest {

    String medicineBoxAreaName;
    String medicineName;
    int quantity;
    String imageUrl;
}
