package com.repill.backend.domain.medicine.repository;

import com.repill.backend.domain.medicine.entity.Medicine;
import com.repill.backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicineJpaRepository extends JpaRepository<Medicine, Long> {

    List<Medicine> findByMemberIdAndDiscardedFalse(Long memberId);
    Optional<Medicine> findMedicineByMemberAndName(Member member, String name);
    List<Medicine> findAllByMemberId(Long memberId);
    void deleteById(Long id);
    Medicine findById(long id);
}
