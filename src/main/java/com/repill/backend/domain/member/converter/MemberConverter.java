package com.repill.backend.domain.member.converter;

import com.repill.backend.domain.medicine.entity.Medicine;
import com.repill.backend.domain.medicine.entity.MedicineType;
import com.repill.backend.domain.member.dto.MemberResponse;
import com.repill.backend.domain.member.entity.Member;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class MemberConverter {

    public static MemberResponse.memberInfoResponse toMemberInfoReponse(
            Member member,
            Integer discardedCount,
            List<MemberResponse.miniMyMedecine> miniMyMydecineList){

        return MemberResponse.memberInfoResponse.builder()
                .memebrId(member.getId())
                .name(member.getName())
                .ecoContribution(member.getEcoContribution())
                .level(member.getLevel())
                .memberRank(member.getMemberRank())
                .participantCount(member.getParticipantCount())
                .discardedCount(discardedCount)
                .myMedicinesCount(miniMyMydecineList.size())
                .miniMyMedecineList(miniMyMydecineList)
                .build();
    }

    public static MemberResponse.miniMyMedecine toMiniMyMedicine(Medicine medicine){
        LocalDate expirationDate = medicine.getExpirationDate();
        long days = ChronoUnit.DAYS.between(LocalDate.now(), expirationDate);
        String dday = days == 0 ? "D-Day" : (days > 0 ? "D-" + days : "D+" + Math.abs(days));

        return MemberResponse.miniMyMedecine.builder()
                .medicineId(medicine.getId())
                .content(medicine.getName() + "-" + expirationDate)
                .D_day(dday)
                .build();
    }

    public static MemberResponse.typeOfMedecine totypeOfMedecine(MedicineType medicineType){
        return MemberResponse.typeOfMedecine.builder()
                .medecineId(medicineType.getId())
                .medecineName(medicineType.getMedicineTypeName())
                .count(0)
                .build();
    }

    public static MemberResponse.StatisticsByTypeOfDrug toStatisticsByTypeOfMedecine(Member member, List<MemberResponse.typeOfMedecine> typeOfMedecineList){
        return MemberResponse.StatisticsByTypeOfDrug.builder()
                .memberId(member.getId())
                .typeOfMedecineList(typeOfMedecineList)
                .build();
    }
}