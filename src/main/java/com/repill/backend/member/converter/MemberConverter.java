package com.repill.backend.member.converter;

import com.repill.backend.medicine.entity.Medicine;
import com.repill.backend.member.dto.MemberResponse;
import com.repill.backend.member.entity.Member;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class MemberConverter {

    public static MemberResponse.memberInfoResponse toMemberInfoReponse(
            Member member,
            Integer discardedCount,
            List<MemberResponse.miniMyMydecine> miniMyMydecineList){

        return MemberResponse.memberInfoResponse.builder()
                .memebrId(member.getId())
                .name(member.getName())
                .ecoContribution(member.getEcoContribution())
                .level(member.getLevel())
                .memberRank(member.getMemberRank())
                .participantCount(member.getParticipantCount())
                .discardedCount(discardedCount)
                .myMedicinesCount(miniMyMydecineList.size())
                .miniMyMydecineList(miniMyMydecineList)
                .build();
    }

    public static MemberResponse.miniMyMydecine toMiniMyMedicine(Medicine medicine){
        LocalDate expirationDate = medicine.getExpirationDate();
        long days = ChronoUnit.DAYS.between(LocalDate.now(), expirationDate);
        String dday = days == 0 ? "D-Day" : (days > 0 ? "D-" + days : "D+" + Math.abs(days));

        return MemberResponse.miniMyMydecine.builder()
                .medicineId(medicine.getId())
                .content(medicine.getName() + "-" + expirationDate)
                .D_day(dday)
                .build();
    }
}