package com.repill.backend.discardrecord.entity;

import com.repill.backend.medicine.entity.Medicine;
import com.repill.backend.medicineboxarea.entity.MedicineBoxArea;
import com.repill.backend.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "discard_record")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DiscardRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_box_area_id", nullable = false)
    private MedicineBoxArea medicineBoxArea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine;

    @Column(nullable = false)
    private LocalDate discardedAt;

    @Column(nullable = false)
    private Integer quantity;

    private String imageUrl;
}
