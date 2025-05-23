package com.repill.backend.medicineboxarea.service;

import com.repill.backend.domain.medicineboxarea.dto.MedicineBoxAreaResponse;
import com.repill.backend.domain.medicineboxarea.entity.MedicineBoxArea;
import com.repill.backend.domain.medicineboxarea.repostory.MedicineBoxAreaJpaRepository;
import com.repill.backend.domain.medicineboxarea.service.MedicineBoxAreaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicineBoxAreaServiceTest {

    @Mock
    MedicineBoxAreaJpaRepository medicineBoxAreaJpaRepository;

    @InjectMocks
    MedicineBoxAreaService medicineBoxAreaService;

    MedicineBoxArea areaA;
    MedicineBoxArea areaB;

    @BeforeEach
    void setUp() {
        areaA = MedicineBoxArea.builder()
                .id(1L)
                .name("A수거함")
                .latitude(37.0)
                .longitude(127.0)
                .address("A주소")
                .telephone("010-1234-5678")
                .build();
        
        areaB = MedicineBoxArea.builder()
                .id(2L)
                .name("B수거함")
                .latitude(37.02)
                .longitude(127.0)
                .address("B주소")
                .telephone("010-1234-5678")
                .build();
    }

    @Test
    @DisplayName("거리순 정렬 테스트")
    void getMedicineBoxAreaList_nearest() {
        // given
        when(medicineBoxAreaJpaRepository.findAll()).thenReturn(Arrays.asList(areaB, areaA));

        // when
        List<MedicineBoxAreaResponse.MedicineBoxAreaDetailResponse> result =
                medicineBoxAreaService.getMedicineBoxAreaList(37.0, 127.0, "nearest");

        // then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getName()).isEqualTo("A수거함");
        assertThat(result.get(1).getName()).isEqualTo("B수거함");
    }

    @Test
    @DisplayName("이름순 정렬 테스트")
    void getMedicineBoxAreaList_name() {
        MedicineBoxArea areaC = MedicineBoxArea.builder()
                .id(3L)
                .name("C수거함")
                .latitude(37.019)
                .longitude(127.0)
                .address("C주소")
                .telephone("010-1234-5678")
                .build();
        when(medicineBoxAreaJpaRepository.findAll()).thenReturn(Arrays.asList(areaB, areaC, areaA));

        List<MedicineBoxAreaResponse.MedicineBoxAreaDetailResponse> result =
                medicineBoxAreaService.getMedicineBoxAreaList(37.0, 127.0, "name");

        // areaC가 3km 이내가 되도록 latitude를 37.019 등으로 조정하면 size가 3이 됩니다.
        assertThat(result.size()).isEqualTo(3);
        assertThat(result.get(0).getName()).isEqualTo("A수거함");
        assertThat(result.get(1).getName()).isEqualTo("B수거함");
        assertThat(result.get(2).getName()).isEqualTo("C수거함");
    }
}
