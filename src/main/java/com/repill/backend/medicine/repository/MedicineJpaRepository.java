package com.repill.backend.medicine.repository;

import com.repill.backend.medicine.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineJpaRepository extends JpaRepository<Medicine, Long> {
}
