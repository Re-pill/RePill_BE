package com.repill.backend.medicineboxarea.repostory;

import com.repill.backend.medicineboxarea.entity.MedicineBoxArea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicineBoxAreaJpaRepository extends JpaRepository<MedicineBoxArea, Long> {

    Optional<MedicineBoxArea> findByName(String name);
}
