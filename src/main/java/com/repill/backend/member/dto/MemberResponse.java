package com.repill.backend.member.dto;

import com.repill.backend.member.entity.Rank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class MemberResponse {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class memberInfoResponse {
        Long memebrId; // 아이디
        String name; // 이름
        Integer ecoContribution; // 환경 기여도
        Integer level; // 레벨
        Rank memberRank; // 랭크
        Integer participantCount; // 참여 수
        Integer discardedCount; // 폐기 약품 충 수량
        Integer myMedicinesCount; // 등록된 MY 약 몇 건인지 Count
        List<miniMyMydecine> miniMyMydecineList; // My 약 mini 표시
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class miniMyMydecine {
        Long medicineId;
        String content;
        String D_day;
    }

}
