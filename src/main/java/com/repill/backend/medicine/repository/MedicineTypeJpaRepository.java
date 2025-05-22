package com.repill.backend.medicine.repository;

import com.repill.backend.medicine.entity.MedicineType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicineTypeJpaRepository extends JpaRepository<MedicineType, Long> {

    Optional<MedicineType> findMedicineTypeByMedicineTypeName(String medicineTypeName);
    List<MedicineType> findAll();
}
