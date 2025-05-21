package com.repill.backend.medicine.entity;

import com.repill.backend.apiPayload.code.status.ErrorStatus;
import com.repill.backend.apiPayload.exception.handler.TestHandler;
import com.repill.backend.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "medicine")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
@Builder
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medicine_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_type_id")
    private MedicineType medicineType;

    @Column(nullable = false)
    private String name;

    private Integer count = 0;

    private LocalDate expirationDate;

    private Boolean discarded = false;

    private LocalDate discardedAt;

    private String discardLocation;

    private Medicine(Member member, MedicineType medicineType, String name, Integer count, LocalDate expirationDate) {
        this.member = member;
        this.medicineType = medicineType;
        this.name = name;
        this.count = count;
        this.expirationDate = expirationDate;
    }

    public static Medicine create(Member member, MedicineType medicineType, String name, Integer count, LocalDate expirationDate) {
        return new Medicine(
                member,
                medicineType,
                name,
                count,
                expirationDate
        );
    }

    public void decreaseCount(int quantity) {
        if (this.count < quantity) {
            throw new TestHandler(ErrorStatus.INSUFFICIENT_MEDICINE_COUNT);
        }

        this.count -= quantity;

        if (this.count == 0) {
            this.discarded = true;
            this.discardedAt = LocalDate.now();
        }
    }
}
