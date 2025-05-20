package com.repill.backend.member.memberService;

import com.repill.backend.apiPayload.code.status.ErrorStatus;
import com.repill.backend.apiPayload.exception.RePillClientException;
import com.repill.backend.discardrecord.repository.DiscardRecordRepository;
import com.repill.backend.medicine.entity.Medicine;
import com.repill.backend.medicine.repository.MedicineJpaRepository;
import com.repill.backend.member.converter.MemberConverter;
import com.repill.backend.member.dto.MemberResponse;
import com.repill.backend.member.entity.Member;
import com.repill.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MedicineJpaRepository medicineJpaRepository;
    private final DiscardRecordRepository discardRecordRepository;

    public MemberResponse.memberInfoResponse getMemberInfo(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RePillClientException(ErrorStatus.USER_NOT_FOUND));
        Integer discardedCount = discardRecordRepository.findAllByMemberId(memberId).size();

        List<Medicine> myMydecineList = medicineJpaRepository. findAllByMemberId(memberId);
        List<MemberResponse.miniMyMydecine> miniMyMydecineList = myMydecineList.stream()
                .map(MemberConverter::toMiniMyMedicine)
                .collect(Collectors.toList());
        MemberResponse.memberInfoResponse result = MemberConverter.toMemberInfoReponse(member,discardedCount,miniMyMydecineList);
        return result;
    } // 마이페이지 유저 정보 가져오기

}
