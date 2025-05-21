package com.repill.backend.discardrecord.service;

import com.repill.backend.apiPayload.code.status.ErrorStatus;
import com.repill.backend.apiPayload.exception.handler.TestHandler;
import com.repill.backend.discardrecord.dto.DiscardRecordRequest;
import com.repill.backend.discardrecord.dto.DiscardRecordResponse;
import com.repill.backend.discardrecord.entity.DiscardRecord;
import com.repill.backend.discardrecord.repository.DiscardRecordJpaRepository;
import com.repill.backend.medicine.entity.Medicine;
import com.repill.backend.medicine.repository.MedicineJpaRepository;
import com.repill.backend.medicineboxarea.entity.MedicineBoxArea;
import com.repill.backend.medicineboxarea.repostory.MedicineBoxAreaJpaRepository;
import com.repill.backend.member.entity.Member;
import com.repill.backend.member.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiscardRecordService {

    private final DiscardRecordJpaRepository discardRecordJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final MedicineBoxAreaJpaRepository medicineBoxAreaJpaRepository;
    private final MedicineJpaRepository medicineJpaRepository;

    @Transactional
    public DiscardRecordResponse createDiscardRecord(Long memberId, DiscardRecordRequest request) {
        Member member = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new TestHandler(ErrorStatus.MEMBER_NOT_FOUND));

        MedicineBoxArea medicineBoxArea = medicineBoxAreaJpaRepository.findByName(request.getMedicineBoxAreaName())
                .orElseThrow(() -> new TestHandler(ErrorStatus.MEDICINE_BOX_AREA_NOT_FOUND));

        Medicine medicine = medicineJpaRepository.findMedicineByMemberAndName(member, request.getMedicineName())
                .orElseThrow(() -> new TestHandler(ErrorStatus.MEDICINE_NOT_FOUND));

        if (medicine.getDiscarded() == true) {
            throw new TestHandler(ErrorStatus.MEDICINE_ALREADY_DISCARDED);
        }

        medicine.decreaseCount(request.getQuantity());

        DiscardRecord discardRecord = DiscardRecord.create(
                member,
                medicineBoxArea,
                medicine,
                java.time.LocalDate.now(),
                request.getQuantity(),
                request.getImageUrl()
        );

        discardRecordJpaRepository.save(discardRecord);

        return new DiscardRecordResponse("폐기 기록이 생성되었습니다.");
    }
}
