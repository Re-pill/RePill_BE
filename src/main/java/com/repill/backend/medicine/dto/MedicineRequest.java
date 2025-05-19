package com.repill.backend.medicine.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicineRequest {

    @NotBlank(message = "약품 이름은 필수입니다.")
    String name;

    @NotNull(message = "약품 개수는 필수입니다.")
    @Min(value = 1, message = "약품 개수는 1개 이상이어야 합니다.")
    Integer count;

    @NotNull(message = "유통기한은 필수입니다.")
    @Future(message = "유통기한은 오늘 이후여야 합니다.")
    LocalDate expirationDate;

    @NotBlank(message = "약품 유형은 필수입니다.")
    String medicineTypeName;
}
