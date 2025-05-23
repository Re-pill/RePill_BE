package com.repill.backend.discardrecord.service;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiscardRecordServiceTest {

    @Mock
    DiscardRecordJpaRepository discardRecordJpaRepository;

    @Mock
    MemberJpaRepository memberJpaRepository;

    @Mock
    MedicineBoxAreaJpaRepository medicineBoxAreaJpaRepository;

    @Mock
    MedicineJpaRepository medicineJpaRepository;

    @InjectMocks
    DiscardRecordService discardRecordService;

    DiscardRecordRequest request;
    Member member;
    MedicineBoxArea boxArea;
    Medicine medicine;

    @BeforeEach
    void setUp() {
        request = DiscardRecordRequest.builder()
                .medicineBoxAreaName("강릉시 폐의약품 수거함 1번")
                .medicineName("타이레놀")
                .quantity(2)
                .build();

        member = Member.builder().id(1L).build();
        boxArea = MedicineBoxArea.builder().id(1L).name("강릉시 폐의약품 수거함 1번").build();
        medicine = Medicine.builder().id(1L).name("타이레놀").count(10).discarded(false).build();
    }

    @Test
    @DisplayName("폐기 기록 생성 성공")
    void createDiscardRecord_success() {
        // given
        when(memberJpaRepository.findById(1L)).thenReturn(Optional.of(member));
        when(medicineBoxAreaJpaRepository.findByName("강릉시 폐의약품 수거함 1번")).thenReturn(Optional.of(boxArea));
        when(medicineJpaRepository.findMedicineByMemberAndName(member, "타이레놀")).thenReturn(Optional.of(medicine));

        // when
        DiscardRecordResponse.DiscardRecordCreateResponse response = discardRecordService.createDiscardRecord(1L, request);

        // then
        verify(medicineJpaRepository, times(1)).findMedicineByMemberAndName(member, "타이레놀");
        verify(discardRecordJpaRepository, times(1)).save(any(DiscardRecord.class));
        assertThat(response.getMessage()).isEqualTo("폐기 기록이 생성되었습니다.");
    }

    @Test
    @DisplayName("회원이 없을 때 예외 발생")
    void createDiscardRecord_memberNotFound() {
        // given
        when(memberJpaRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> discardRecordService.createDiscardRecord(1L, request))
                .isInstanceOf(TestHandler.class);

        verify(discardRecordJpaRepository, never()).save(any());
    }

    @Test
    @DisplayName("수거함이 없을 때 예외 발생")
    void createDiscardRecord_boxAreaNotFound() {
        // given
        when(memberJpaRepository.findById(1L)).thenReturn(Optional.of(member));
        when(medicineBoxAreaJpaRepository.findByName("강릉시 폐의약품 수거함 1번")).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> discardRecordService.createDiscardRecord(1L, request))
                .isInstanceOf(TestHandler.class);

        verify(discardRecordJpaRepository, never()).save(any());
    }

    @Test
    @DisplayName("약품이 없을 때 예외 발생")
    void createDiscardRecord_medicineNotFound() {
        // given
        when(memberJpaRepository.findById(1L)).thenReturn(Optional.of(member));
        when(medicineBoxAreaJpaRepository.findByName("강릉시 폐의약품 수거함 1번")).thenReturn(Optional.of(boxArea));
        when(medicineJpaRepository.findMedicineByMemberAndName(member, "타이레놀")).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> discardRecordService.createDiscardRecord(1L, request))
                .isInstanceOf(TestHandler.class);

        verify(discardRecordJpaRepository, never()).save(any());
    }

    @Test
    @DisplayName("이미 폐기된 약품일 때 예외 발생")
    void createDiscardRecord_alreadyDiscarded() {
        // given
        medicine = Medicine.builder()
                .id(1L)
                .name("타이레놀")
                .count(10)
                .discarded(Boolean.TRUE)
                .build();

        when(memberJpaRepository.findById(1L)).thenReturn(Optional.of(member));
        when(medicineBoxAreaJpaRepository.findByName("강릉시 폐의약품 수거함 1번")).thenReturn(Optional.of(boxArea));
        when(medicineJpaRepository.findMedicineByMemberAndName(member, "타이레놀")).thenReturn(Optional.of(medicine));

        // when & then
        assertThatThrownBy(() -> discardRecordService.createDiscardRecord(1L, request))
                .isInstanceOf(TestHandler.class);

        verify(discardRecordJpaRepository, never()).save(any());
    }

    @Test
    @DisplayName("폐기 기록 상세 조회 성공")
    void getDiscardRecordDetail_success() {
        // given
        Long recordId = 1L;
        Medicine medicine = Medicine.builder()
                .id(1L)
                .name("타이레놀")
                .expirationDate(java.time.LocalDate.of(2025, 6, 1))
                .build();
        MedicineBoxArea boxArea = MedicineBoxArea.builder()
                .id(1L)
                .address("강릉시 폐의약품 수거함 1번")
                .build();
        DiscardRecord discardRecord = DiscardRecord.builder()
                .id(recordId)
                .medicine(medicine)
                .medicineBoxArea(boxArea)
                .discardedAt(java.time.LocalDate.of(2024, 6, 1))
                .build();

        when(discardRecordJpaRepository.findById(recordId)).thenReturn(Optional.of(discardRecord));

        // when
        DiscardRecordResponse.DiscardRecordDetailResponse response = discardRecordService.getDiscardRecordDetail(recordId);

        // then
        assertThat(response.getName()).isEqualTo("타이레놀");
        assertThat(response.getExpirationDate()).isEqualTo(java.time.LocalDate.of(2025, 6, 1));
        assertThat(response.getDiscardedAt()).isEqualTo(java.time.LocalDate.of(2024, 6, 1));
        assertThat(response.getDiscardLocation()).isEqualTo("강릉시 폐의약품 수거함 1번");
    }

    @Test
    @DisplayName("폐기 기록 상세 조회 - 기록이 없을 때 예외 발생")
    void getDiscardRecordDetail_notFound() {
        // given
        Long recordId = 999L;
        when(discardRecordJpaRepository.findById(recordId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> discardRecordService.getDiscardRecordDetail(recordId))
                .isInstanceOf(TestHandler.class);
    }

    @Test
    @DisplayName("폐기 기록 목록 조회 성공")
    void getDiscardRecordList_success() {
        // given
        Long memberId = 1L;
        when(memberJpaRepository.findById(memberId)).thenReturn(Optional.of(member));

        Medicine medicine1 = Medicine.builder().id(1L).name("타이레놀").expirationDate(java.time.LocalDate.of(2025, 6, 1)).build();
        Medicine medicine2 = Medicine.builder().id(2L).name("어린이부루펜").expirationDate(java.time.LocalDate.of(2024, 12, 31)).build();
        MedicineBoxArea boxArea = MedicineBoxArea.builder().id(1L).address("강릉시 폐의약품 수거함 1번").build();

        DiscardRecord record1 = DiscardRecord.builder()
                .id(1L)
                .medicine(medicine1)
                .medicineBoxArea(boxArea)
                .discardedAt(java.time.LocalDate.of(2024, 6, 1))
                .build();
        DiscardRecord record2 = DiscardRecord.builder()
                .id(2L)
                .medicine(medicine2)
                .medicineBoxArea(boxArea)
                .discardedAt(java.time.LocalDate.of(2024, 6, 2))
                .build();

        when(discardRecordJpaRepository.findAllByMember(member)).thenReturn(List.of(record1, record2));

        // when
        DiscardRecordResponse.DiscardRecordListResponse response = discardRecordService.getDiscardRecordList(memberId);

        // then
        assertThat(response.getTotalCount()).isEqualTo(2);
        assertThat(response.getDiscardRecordDetailResponseList()).hasSize(2);
        assertThat(response.getDiscardRecordDetailResponseList().get(0).getName()).isEqualTo("타이레놀");
        assertThat(response.getDiscardRecordDetailResponseList().get(1).getName()).isEqualTo("어린이부루펜");
    }

    @Test
    @DisplayName("폐기 기록 목록 조회 - 회원이 없을 때 예외 발생")
    void getDiscardRecordList_memberNotFound() {
        // given
        Long memberId = 999L;
        when(memberJpaRepository.findById(memberId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> discardRecordService.getDiscardRecordList(memberId))
                .isInstanceOf(TestHandler.class);

        verify(discardRecordJpaRepository, never()).findAllByMember(any());
    }
}
