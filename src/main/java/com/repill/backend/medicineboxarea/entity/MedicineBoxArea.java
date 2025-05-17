package com.repill.backend.medicineboxarea.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "medicine_box_area")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MedicineBoxArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medicine_box_area_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    private String address;

    private Double latitude;

    private Double longitude;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LocationType locationType;
}
