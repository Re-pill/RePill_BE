package com.repill.backend.medicine.repository;

import com.repill.backend.medicine.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicineJpaRepository extends JpaRepository<Medicine, Long> {

    List<Medicine> findByMemberIdAndDiscardedFalse(Long memberId);
    List<Medicine> findAllByMemberId(Long memberId);
    void deleteById(Long id);
    Medicine findById(long id);
}
