package com.repill.backend.medicine.entity;

import com.repill.backend.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "medicine")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medicine_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private String name;

    private Integer count = 0;

    private LocalDate expirationDate;

    private Boolean discarded = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_type_id", nullable = false)
    private MedicineType medicineType;

    private Medicine(Member member, String name, Integer count, LocalDate expirationDate, MedicineType medicineType) {
        this.member = member;
        this.name = name;
        this.count = count;
        this.expirationDate = expirationDate;
        this.medicineType = medicineType;
    }

    public static Medicine create(Member member, String name, Integer count, LocalDate expirationDate, MedicineType medicineType) {
        return new Medicine(
                member,
                name,
                count,
                expirationDate,
                medicineType
        );
    }
}
