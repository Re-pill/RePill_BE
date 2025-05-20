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
import com.repill.backend.member.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MedicineService {

    private final MedicineJpaRepository medicineJpaRepository;
    private final MedicineTypeJpaRepository medicineTypeRepository;
    private final MemberJpaRepository memberRepository;

    @Transactional
    public MedicineResponse.MedicineDetailResponse createMedicine(MedicineRequest request) {
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new TestHandler(ErrorStatus.MEMBER_NOT_FOUND));

        MedicineType medicineType = medicineTypeRepository.findMedicineTypeByMedicineTypeName(request.getMedicineTypeName())
                .orElseThrow(() -> new TestHandler(ErrorStatus.MEDICINE_TYPE_NOT_FOUND));

        Medicine medicine = Medicine.create(member, medicineType, request.getName(), request.getCount(), request.getExpirationDate());
        medicineJpaRepository.save(medicine);

        return MedicineResponse.MedicineDetailResponse.builder()
                .medicineId(medicine.getId())
                .medicineTypeName(medicineType.getMedicineTypeName())
                .name(medicine.getName())
                .count(medicine.getCount())
                .expirationDate(medicine.getExpirationDate())
                .discarded(false)
                .build();
    }

    public MedicineResponse.MedicineDDayListResponse getDDayList(Long memberId) {
        List<Medicine> medicineList = medicineJpaRepository.findByMemberIdAndDiscardedFalse(memberId);

        List<MedicineResponse.MedicineDDayResponse> dDayListResponse = medicineList.stream()
                .map(medicine -> {
                    long dDay = ChronoUnit.DAYS.between(LocalDate.now(), medicine.getExpirationDate());
                    return MedicineResponse.MedicineDDayResponse.builder()
                            .name(medicine.getName())
                            .expirationDate(medicine.getExpirationDate())
                            .dDay((int) dDay)
                            .build();
                })
                .sorted(Comparator.comparingInt(MedicineResponse.MedicineDDayResponse::getDDay))
                .toList();

        return new MedicineResponse.MedicineDDayListResponse(dDayListResponse.size(), dDayListResponse);
    }

    public MedicineResponse.MedicineDetailResponse getMedicineDetail(Long medicineId) {
        Medicine medicine = medicineJpaRepository.findById(medicineId)
                .orElseThrow(() -> new TestHandler(ErrorStatus.MEDICINE_TYPE_NOT_FOUND));

        return MedicineResponse.MedicineDetailResponse.builder()
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
}
