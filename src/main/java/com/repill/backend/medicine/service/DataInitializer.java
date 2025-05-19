package com.repill.backend.medicine.service;

import com.repill.backend.medicine.entity.MedicineType;
import com.repill.backend.medicine.repository.MedicineTypeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final MedicineTypeJpaRepository medicineTypeRepository;

    @Bean
    public ApplicationRunner initMedicineTypes() {
        return args -> {
            if (medicineTypeRepository.count() == 0) {
                medicineTypeRepository.save(MedicineType.builder().medicineTypeName("알약").build());
                medicineTypeRepository.save(MedicineType.builder().medicineTypeName("액상약").build());
                medicineTypeRepository.save(MedicineType.builder().medicineTypeName("주사제").build());
                medicineTypeRepository.save(MedicineType.builder().medicineTypeName("연고").build());
            }
        };
    }
}