package com.repill.backend.medicine.service;

import com.repill.backend.apiPayload.exception.handler.TestHandler;
import com.repill.backend.medicine.dto.MedicineRequest;
import com.repill.backend.medicine.dto.MedicineResponse;
import com.repill.backend.medicine.entity.Medicine;
import com.repill.backend.medicine.entity.MedicineType;
import com.repill.backend.medicine.repository.MedicineJpaRepository;
import com.repill.backend.medicine.repository.MedicineTypeJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
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

        // when
        MedicineResponse.MedicineDetailResponse response = medicineService.createMedicine(request);

        // then
        verify(medicineJpaRepository, times(1)).save(medicineCaptor.capture());
        Medicine savedMedicine = medicineCaptor.getValue();

        assertThat(response.getName()).isEqualTo("타이레놀");
        assertThat(response.getMedicineTypeName()).isEqualTo("알약");
        assertThat(response.getIsDiscarded()).isFalse();
        assertThat(savedMedicine.getName()).isEqualTo("타이레놀");
        assertThat(savedMedicine.getCount()).isEqualTo(10);
    }

    @Test
    @DisplayName("약품 타입이 없을 때 예외 발생")
    void createMedicine_typeNotFound() {
        // given
        when(medicineTypeRepository.findMedicineTypeByMedicineTypeName("알약"))
                .thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> medicineService.createMedicine(request))
                .isInstanceOf(TestHandler.class);
        verify(medicineJpaRepository, never()).save(any());
    }
}
