package com.repill.backend.domain.medicineboxarea.repostory;

import com.repill.backend.domain.medicineboxarea.entity.MedicineBoxArea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicineBoxAreaJpaRepository extends JpaRepository<MedicineBoxArea, Long> {

    Optional<MedicineBoxArea> findByName(String name);
    MedicineBoxArea findById(long id);
}
