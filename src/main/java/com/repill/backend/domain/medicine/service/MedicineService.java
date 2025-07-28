package com.repill.backend.domain.medicine.service;

import com.repill.backend.apiPayload.code.status.ErrorStatus;
import com.repill.backend.apiPayload.exception.handler.TestHandler;
import com.repill.backend.domain.medicine.dto.*;
import com.repill.backend.domain.medicine.entity.Medicine;
import com.repill.backend.domain.medicine.entity.MedicineType;
import com.repill.backend.domain.medicine.repository.MedicineJpaRepository;
import com.repill.backend.domain.medicine.repository.MedicineTypeJpaRepository;
import com.repill.backend.domain.member.entity.Member;
import com.repill.backend.domain.member.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MedicineService {

    private final MemberJpaRepository memberJpaRepository;
    private final MedicineJpaRepository medicineJpaRepository;
    private final MedicineTypeJpaRepository medicineTypeJpaRepository;


    @Transactional
    public MedicineDetailResponse createMedicine(Long memberId, MedicineRequest request) {
        Member member = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new TestHandler(ErrorStatus.MEMBER_NOT_FOUND));

        MedicineType medicineType = medicineTypeJpaRepository.findMedicineTypeByMedicineTypeName(request.medicineTypeName())
                .orElseThrow(() -> new TestHandler(ErrorStatus.MEDICINE_TYPE_NOT_FOUND));

        Medicine medicine = Medicine.create(member, medicineType, request.name(), request.count(), request.expirationDate());
        medicineJpaRepository.save(medicine);

        return MedicineDetailResponse.builder()
                .medicineId(medicine.getId())
                .medicineTypeName(medicineType.getMedicineTypeName())
                .name(medicine.getName())
                .count(medicine.getCount())
                .expirationDate(medicine.getExpirationDate())
                .discarded(false)
                .build();
    }

    public MedicineDDayListResponse getDDayList(Long memberId) {
        List<Medicine> medicineList = medicineJpaRepository.findByMemberIdAndDiscardedFalse(memberId);

        List<MedicineDDayResponse> dDayListResponse = medicineList.stream()
                .map(medicine -> {
                    long dDay = ChronoUnit.DAYS.between(LocalDate.now(), medicine.getExpirationDate());
                    return MedicineDDayResponse.builder()
                            .name(medicine.getName())
                            .expirationDate(medicine.getExpirationDate())
                            .dDay((int) dDay)
                            .build();
                })
                .sorted(Comparator.comparingInt(MedicineDDayResponse::dDay))
                .toList();

        return new MedicineDDayListResponse(dDayListResponse.size(), dDayListResponse);
    }

    public MedicineDetailResponse getMedicineDetail(Long medicineId) {
        Medicine medicine = medicineJpaRepository.findById(medicineId)
                .orElseThrow(() -> new TestHandler(ErrorStatus.MEDICINE_TYPE_NOT_FOUND));

        return MedicineDetailResponse.builder()
                .medicineId(medicine.getId())
                .name(medicine.getName())
                .count(medicine.getCount())
                .expirationDate(medicine.getExpirationDate())
                .discarded(medicine.getDiscarded())
                .discardedAt(medicine.getDiscardedAt())
                .discardLocation(medicine.getDiscardLocation())
                .medicineTypeName(medicine.getMedicineType().getMedicineTypeName())
                .build();
    }

    @Transactional
    public void deleteMedicine(Long medicineId, Long memberId) {
        Medicine medicine = medicineJpaRepository.findById(medicineId)
                .orElseThrow(() -> new TestHandler(ErrorStatus.MEDICINE_NOT_FOUND));
        Member member = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new TestHandler(ErrorStatus.MEMBER_NOT_FOUND));

        if (!medicine.getMember().equals(member)) {
            throw new TestHandler(ErrorStatus.MEDICINE_NOT_MEMBER);
        }
        medicineJpaRepository.delete(medicine);
    }

    @Transactional
    public void patchMedicine(Long medicineId, Long memberId, PatchMedicineRequest request) {
        Medicine medicine = medicineJpaRepository.findById(medicineId)
                .orElseThrow(() -> new TestHandler(ErrorStatus.MEDICINE_NOT_FOUND));

        Member member = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new TestHandler(ErrorStatus.MEMBER_NOT_FOUND));

        if (!medicine.getMember().equals(member)) {
            throw new TestHandler(ErrorStatus.MEDICINE_NOT_MEMBER);
        }

        MedicineType medicineType = medicineTypeJpaRepository.findMedicineTypeByMedicineTypeName(request.medicineTypeName())
                .orElseThrow(() -> new TestHandler(ErrorStatus.MEDICINE_TYPE_NOT_FOUND));

        medicine.changeMedicineInfo(request, medicineType);
    }
}
