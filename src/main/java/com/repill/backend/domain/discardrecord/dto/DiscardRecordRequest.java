package com.repill.backend.domain.discardrecord.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscardRecordRequest {

    public String medicineBoxAreaName;
    public String medicineName;
    public int quantity;
}
