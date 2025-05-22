package com.repill.backend.discardrecord.entity;

import com.repill.backend.medicine.entity.Medicine;
import com.repill.backend.medicineboxarea.entity.MedicineBoxArea;
import com.repill.backend.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "discard_record")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
@Builder
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

    private DiscardRecord(Member member, MedicineBoxArea medicineBoxArea, Medicine medicine, LocalDate discardedAt, Integer quantity, String imageUrl) {
        this.member = member;
        this.medicineBoxArea = medicineBoxArea;
        this.medicine = medicine;
        this.discardedAt = discardedAt;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }

    public static DiscardRecord create(Member member, MedicineBoxArea medicineBoxArea, Medicine medicine, LocalDate discardedAt, Integer quantity, String imageUrl) {
        return new DiscardRecord(
                member,
                medicineBoxArea,
                medicine,
                discardedAt,
                quantity,
                imageUrl);
    }
}
