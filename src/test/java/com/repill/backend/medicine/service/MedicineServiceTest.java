package com.repill.backend.medicine.service;

import com.repill.backend.apiPayload.exception.handler.TestHandler;
import com.repill.backend.domain.medicine.dto.MedicineRequest;
import com.repill.backend.domain.medicine.dto.MedicineResponse;
import com.repill.backend.domain.medicine.entity.Medicine;
import com.repill.backend.domain.medicine.entity.MedicineType;
import com.repill.backend.domain.medicine.repository.MedicineJpaRepository;
import com.repill.backend.domain.medicine.repository.MedicineTypeJpaRepository;
import com.repill.backend.domain.medicine.service.MedicineService;
import com.repill.backend.domain.member.entity.Member;
import com.repill.backend.domain.member.repository.MemberJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicineServiceTest {

    @Mock
    MedicineJpaRepository medicineJpaRepository;

    @Mock
    MedicineTypeJpaRepository medicineTypeRepository;

    @Mock
    MemberJpaRepository memberRepository;

    @InjectMocks
    MedicineService medicineService;

    MedicineRequest request;
    MedicineType medicineType;

    @BeforeEach
    void setUp() {
        request = MedicineRequest.builder()
                .name("타이레놀")
                .count(10)
                .expirationDate(LocalDate.now().plusDays(30))
                .medicineTypeName("알약")
                .build();

        medicineType = MedicineType.builder()
                .id(1L)
                .medicineTypeName("알약")
                .build();
    }

    @Test
    @DisplayName("약품 등록 성공")
    void createMedicine_success() {
        // given
        when(medicineTypeRepository.findMedicineTypeByMedicineTypeName("알약"))
                .thenReturn(Optional.of(medicineType));

        ArgumentCaptor<Medicine> medicineCaptor = ArgumentCaptor.forClass(Medicine.class);

        Member member = Member.builder().id(1L).build();
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        // when
        MedicineResponse.MedicineDetailResponse response = medicineService.createMedicine(1L, request);

        // then
        verify(medicineJpaRepository, times(1)).save(medicineCaptor.capture());
        Medicine savedMedicine = medicineCaptor.getValue();

        assertThat(response.getName()).isEqualTo("타이레놀");
        assertThat(response.getMedicineTypeName()).isEqualTo("알약");
        assertThat(response.getDiscarded()).isFalse();
        assertThat(savedMedicine.getName()).isEqualTo("타이레놀");
        assertThat(savedMedicine.getCount()).isEqualTo(10);
    }

    @Test
    @DisplayName("약품 타입이 없을 때 예외 발생")
    void createMedicine_typeNotFound() {
        // given
        lenient().when(memberRepository.findById(1L)).thenReturn(Optional.of(Member.builder().id(1L).build()));
        when(medicineTypeRepository.findMedicineTypeByMedicineTypeName("알약"))
                .thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> medicineService.createMedicine(1L, request))
                .isInstanceOf(TestHandler.class);
        verify(medicineJpaRepository, never()).save(any());
    }

    @Test
    @DisplayName("D-Day 리스트 정상 반환")
    void getDDayList_success() {
        Long memberId = 1L;
        Medicine med1 = Medicine.builder()
                .id(1L)
                .name("타이레놀")
                .expirationDate(LocalDate.now().plusDays(10))
                .build();
        Medicine med2 = Medicine.builder()
                .id(2L)
                .name("어린이시럽")
                .expirationDate(LocalDate.now().plusDays(5))
                .build();

        when(medicineJpaRepository.findByMemberIdAndDiscardedFalse(memberId))
                .thenReturn(List.of(med1, med2));

        MedicineResponse.MedicineDDayListResponse response = medicineService.getDDayList(memberId);

        assertThat(response.getTotalCount()).isEqualTo(2);
        assertThat(response.getDDayResponseList()).hasSize(2);
        assertThat(response.getDDayResponseList().get(0).getName()).isEqualTo("어린이시럽");
        assertThat(response.getDDayResponseList().get(0).getDDay()).isEqualTo(5);
        assertThat(response.getDDayResponseList().get(1).getName()).isEqualTo("타이레놀");
        assertThat(response.getDDayResponseList().get(1).getDDay()).isEqualTo(10);
    }

    @Test
    @DisplayName("D-Day 리스트 - 약품이 없을 때")
    void getDDayList_empty() {
        Long memberId = 1L;
        when(medicineJpaRepository.findByMemberIdAndDiscardedFalse(memberId))
                .thenReturn(List.of());

        MedicineResponse.MedicineDDayListResponse response = medicineService.getDDayList(memberId);

        assertThat(response.getTotalCount()).isZero();
        assertThat(response.getDDayResponseList()).isEmpty();
    }

    @Test
    @DisplayName("약품 디테일 조회 성공")
    void getMedicineDetail_success() {
        // given
        Long medicineId = 1L;
        MedicineType medicineType = MedicineType.builder()
                .id(1L)
                .medicineTypeName("알약")
                .build();
        Medicine medicine = Medicine.builder()
                .id(medicineId)
                .name("타이레놀")
                .count(2)
                .expirationDate(LocalDate.of(2025, 6, 1))
                .discarded(true)
                .discardedAt(LocalDate.of(2025, 5, 15))
                .discardLocation("강릉시 폐의약품 수거함 1번")
                .medicineType(medicineType)
                .build();

        when(medicineJpaRepository.findById(medicineId)).thenReturn(Optional.of(medicine));

        // when
        MedicineResponse.MedicineDetailResponse response = medicineService.getMedicineDetail(medicineId);

        // then
        assertThat(response.getMedicineId()).isEqualTo(medicineId);
        assertThat(response.getName()).isEqualTo("타이레놀");
        assertThat(response.getCount()).isEqualTo(2);
        assertThat(response.getExpirationDate()).isEqualTo(LocalDate.of(2025, 6, 1));
        assertThat(response.getDiscarded()).isTrue();
        assertThat(response.getDiscardedAt()).isEqualTo(LocalDate.of(2025, 5, 15));
        assertThat(response.getDiscardLocation()).isEqualTo("강릉시 폐의약품 수거함 1번");
        assertThat(response.getMedicineTypeName()).isEqualTo("알약");
    }

    @Test
    @DisplayName("약품 디테일 조회 - 존재하지 않는 약품 예외")
    void getMedicineDetail_notFound() {
        // given
        Long medicineId = 999L;
        when(medicineJpaRepository.findById(medicineId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> medicineService.getMedicineDetail(medicineId))
                .isInstanceOf(TestHandler.class);
    }
}
