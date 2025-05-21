package com.repill.backend.medicine.repository;

import com.repill.backend.medicine.entity.Medicine;
import com.repill.backend.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicineJpaRepository extends JpaRepository<Medicine, Long> {

    List<Medicine> findByMemberIdAndDiscardedFalse(Long memberId);

    Optional<Medicine> findMedicineByMemberAndName(Member member, String name);
}
