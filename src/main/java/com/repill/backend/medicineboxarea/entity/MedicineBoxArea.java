package com.repill.backend.medicineboxarea.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "medicine_box_area")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
@Builder
public class MedicineBoxArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medicine_box_area_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    private String address;

    // 경도 데이터, 좌표 X
    private Double longitude;

    // 위도 데이터, 좌표 Y
    private Double latitude;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LocationType locationType;

    private String telephone;
}
