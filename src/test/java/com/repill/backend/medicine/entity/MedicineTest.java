package com.repill.backend.medicine.entity;

import com.repill.backend.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MedicineTest {

    @Test
    @DisplayName("Medicine 생성 테스트")
    void createMedicine_withMockedDependencies() {
        // given
        Member mockMember = Mockito.mock(Member.class);
        MedicineType mockType = Mockito.mock(MedicineType.class);

        String name = "타이레놀";
        int count = 10;
        LocalDate expirationDate = LocalDate.now().plusDays(30);

        // when
        Medicine medicine = Medicine.create(mockMember, name, count, expirationDate, mockType);

        // then
        assertEquals(name, medicine.getName());
        assertEquals(count, medicine.getCount());
        assertEquals(expirationDate, medicine.getExpirationDate());
        assertEquals(false, medicine.getDiscarded());
        assertEquals(mockMember, medicine.getMember());
        assertEquals(mockType, medicine.getMedicineType());
    }
}
