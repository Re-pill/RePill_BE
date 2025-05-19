package com.repill.backend.medicine.service;

import com.repill.backend.apiPayload.code.status.ErrorStatus;
import com.repill.backend.apiPayload.exception.handler.TestHandler;
import com.repill.backend.medicine.dto.MedicineRequest;
import com.repill.backend.medicine.dto.MedicineResponse;
import com.repill.backend.medicine.entity.Medicine;
import com.repill.backend.medicine.entity.MedicineType;
import com.repill.backend.medicine.repository.MedicineJpaRepository;
import com.repill.backend.medicine.repository.MedicineTypeJpaRepository;
import com.repill.backend.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MedicineService {

    private final MedicineJpaRepository medicineJpaRepository;
    private final MedicineTypeJpaRepository medicineTypeRepository;

    @Transactional
    public MedicineResponse.MedicineDetailResponse createMedicine(MedicineRequest request) {
        Member fakeMember = Member.builder()
                .id(1L)
                .name("Fake User")
                .email("fake@email.com")
                .build();

        MedicineType medicineType = medicineTypeRepository.findMedicineTypeByMedicineTypeName(request.getMedicineTypeName())
                .orElseThrow(() -> new TestHandler(ErrorStatus.MEDICINE_TYPE_NOT_FOUND));

        Medicine medicine = Medicine.create(fakeMember, medicineType, request.getName(), request.getCount(), request.getExpirationDate());
        medicineJpaRepository.save(medicine);

        return MedicineResponse.MedicineDetailResponse.builder()
                .medicineId(medicine.getId())
                .medicineTypeName(medicineType.getMedicineTypeName())
                .name(medicine.getName())
                .count(medicine.getCount())
                .expirationDate(medicine.getExpirationDate())
                .isDiscarded(false)
                .build();
    }
}
