package com.repill.backend.discardrecord.entity;

import com.repill.backend.medicine.entity.Medicine;
import com.repill.backend.medicineboxarea.entity.MedicineBoxArea;
import com.repill.backend.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DiscardRecordTest {

    @Test
    @DisplayName("DiscardRecord 생성 테스트")
    void createDiscardRecordTest() {
        // given
        Member mockMember = Mockito.mock(Member.class);
        MedicineBoxArea mockArea = Mockito.mock(MedicineBoxArea.class);
        Medicine mockMedicine = Mockito.mock(Medicine.class);

        LocalDate discardedAt = LocalDate.now();
        int quantity = 5;

        // when
        DiscardRecord record = DiscardRecord.create(mockMember, mockArea, mockMedicine, discardedAt, quantity);

        // then
        assertEquals(mockMember, record.getMember());
        assertEquals(mockArea, record.getMedicineBoxArea());
        assertEquals(mockMedicine, record.getMedicine());
        assertEquals(discardedAt, record.getDiscardedAt());
        assertEquals(quantity, record.getQuantity());
    }
}
