package com.repill.backend.discardrecord.dto;

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
