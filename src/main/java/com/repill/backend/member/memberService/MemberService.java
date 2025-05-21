package com.repill.backend.member.memberService;

import com.repill.backend.apiPayload.code.status.ErrorStatus;
import com.repill.backend.apiPayload.exception.RePillClientException;
import com.repill.backend.apiPayload.exception.handler.TestHandler;
import com.repill.backend.discardrecord.entity.DiscardRecord;
import com.repill.backend.discardrecord.repository.DiscardRecordJpaRepository;
import com.repill.backend.discardrecord.repository.DiscardRecordRepository;
import com.repill.backend.medicine.entity.Medicine;
import com.repill.backend.medicine.entity.MedicineType;
import com.repill.backend.medicine.repository.MedicineJpaRepository;
import com.repill.backend.medicine.repository.MedicineTypeJpaRepository;
import com.repill.backend.member.converter.MemberConverter;
import com.repill.backend.member.dto.MemberResponse;
import com.repill.backend.member.entity.Member;
import com.repill.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MedicineJpaRepository medicineJpaRepository;
    private final DiscardRecordRepository discardRecordRepository;
    private final DiscardRecordJpaRepository discardRecordJpaRepository;
    private final MedicineTypeJpaRepository medicineTypeJpaRepository;

    public MemberResponse.memberInfoResponse getMemberInfo(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RePillClientException(ErrorStatus.USER_NOT_FOUND));
        Integer discardedCount = discardRecordRepository.findAllByMemberId(memberId).size();

        List<Medicine> myMydecineList = medicineJpaRepository. findAllByMemberId(memberId);
        List<MemberResponse.miniMyMedecine> miniMyMydecineList = myMydecineList.stream()
                .map(MemberConverter::toMiniMyMedicine)
                .collect(Collectors.toList());
        MemberResponse.memberInfoResponse result = MemberConverter.toMemberInfoReponse(member,discardedCount,miniMyMydecineList);
        return result;
    }

    public List<MemberResponse.typeOfMedecine> getDiscardMedecineList(Long memberId, List<DiscardRecord> discardList) {
        List<MedicineType> typeList = medicineTypeJpaRepository.findAll();
        Map<String, Integer> discardCountMap = new HashMap<>();
        for (DiscardRecord discard : discardList) {
            String typeName = discard.getMedicine().getMedicineType().getMedicineTypeName();
            discardCountMap.put(typeName, discardCountMap.getOrDefault(typeName, 0) + 1);
        }
        List<MemberResponse.typeOfMedecine> resultList = new ArrayList<>();
        for (MedicineType type : typeList) {
            MemberResponse.typeOfMedecine response = MemberConverter.totypeOfMedecine(type);
            int count = discardCountMap.getOrDefault(type.getMedicineTypeName(), 0);
            response.setCount(count);
            resultList.add(response);
        }
        return resultList;
    }

    public MemberResponse.StatisticsByTypeOfDrug getMedicineTypeStatistics(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new TestHandler(ErrorStatus.MEMBER_NOT_FOUND));
        List<DiscardRecord> discardList =  discardRecordJpaRepository.findAllByMember(member);
        List<MemberResponse.typeOfMedecine> DiscardMedicineList = getDiscardMedecineList(memberId,discardList);
        return MemberConverter.toStatisticsByTypeOfMedecine(member, DiscardMedicineList);
    }
}
