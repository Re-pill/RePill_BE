package com.repill.backend.domain.discardrecord.service;

import com.repill.backend.apiPayload.code.status.ErrorStatus;
import com.repill.backend.apiPayload.exception.handler.TestHandler;
import com.repill.backend.domain.discardrecord.dto.DiscardRecordRequest;
import com.repill.backend.domain.discardrecord.dto.DiscardRecordResponse;
import com.repill.backend.domain.discardrecord.entity.DiscardRecord;
import com.repill.backend.domain.discardrecord.repository.DiscardRecordJpaRepository;
import com.repill.backend.domain.medicine.entity.Medicine;
import com.repill.backend.domain.medicine.repository.MedicineJpaRepository;
import com.repill.backend.domain.medicineboxarea.entity.MedicineBoxArea;
import com.repill.backend.domain.medicineboxarea.repostory.MedicineBoxAreaJpaRepository;
import com.repill.backend.domain.member.entity.Member;
import com.repill.backend.domain.member.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiscardRecordService {

    private final DiscardRecordJpaRepository discardRecordJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final MedicineBoxAreaJpaRepository medicineBoxAreaJpaRepository;
    private final MedicineJpaRepository medicineJpaRepository;

    @Transactional
    public DiscardRecordResponse.DiscardRecordCreateResponse createDiscardRecord(Long memberId, DiscardRecordRequest request) {
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
                request.getQuantity()
        );

        discardRecordJpaRepository.save(discardRecord);

        return new DiscardRecordResponse.DiscardRecordCreateResponse("폐기 기록이 생성되었습니다.");
    }

    public DiscardRecordResponse.DiscardRecordDetailResponse getDiscardRecordDetail(Long recordId) {
        DiscardRecord discardRecord = discardRecordJpaRepository.findById(recordId)
                .orElseThrow(() -> new TestHandler(ErrorStatus.DISCARD_RECORD_NOT_FOUND));

        return DiscardRecordResponse.DiscardRecordDetailResponse.builder()
                .name(discardRecord.getMedicine().getName())
                .expirationDate(discardRecord.getMedicine().getExpirationDate())
                .discardedAt(discardRecord.getDiscardedAt())
                .discardLocation(discardRecord.getMedicineBoxArea().getAddress())
                .build();
    }

    public DiscardRecordResponse.DiscardRecordListResponse getDiscardRecordList(Long memberId) {
        Member member = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new TestHandler(ErrorStatus.MEMBER_NOT_FOUND));

        List<DiscardRecord> discardRecordList = discardRecordJpaRepository.findAllByMember(member);

        List<DiscardRecordResponse.DiscardRecordDetailResponse> discardRecordResponseList = discardRecordList.stream()
                .map(discardRecord -> DiscardRecordResponse.DiscardRecordDetailResponse.builder()
                        .name(discardRecord.getMedicine().getName())
                        .expirationDate(discardRecord.getMedicine().getExpirationDate())
                        .discardedAt(discardRecord.getDiscardedAt())
                        .discardLocation(discardRecord.getMedicineBoxArea().getAddress())
                        .build())
                .toList();

        return new DiscardRecordResponse.DiscardRecordListResponse(discardRecordResponseList.size(), discardRecordResponseList);
    }
}
