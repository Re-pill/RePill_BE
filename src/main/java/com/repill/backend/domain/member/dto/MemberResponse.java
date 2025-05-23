package com.repill.backend.domain.member.dto;

import com.repill.backend.domain.member.entity.Rank;
import lombok.*;

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
        List<miniMyMedecine> miniMyMedecineList; // My 약 mini 표시
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class miniMyMedecine {
        Long medicineId;
        String content;
        String D_day;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StatisticsByTypeOfDrug {
        Long memberId;
        List<typeOfMedecine> typeOfMedecineList;
    }

    @Getter
    @Builder
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class typeOfMedecine {
        Long medecineId;
        String medecineName;
        Integer count;
    }
}
