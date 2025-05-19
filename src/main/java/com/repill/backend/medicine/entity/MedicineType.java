package com.repill.backend.medicine.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "medicine_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
@Builder
public class MedicineType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medicine_type_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String medicineTypeName;
}
