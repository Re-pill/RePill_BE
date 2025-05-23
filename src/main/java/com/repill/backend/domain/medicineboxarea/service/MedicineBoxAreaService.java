package com.repill.backend.domain.medicineboxarea.service;

import com.repill.backend.apiPayload.code.status.ErrorStatus;
import com.repill.backend.apiPayload.exception.handler.TestHandler;
import com.repill.backend.domain.medicineboxarea.dto.MedicineBoxAreaResponse;
import com.repill.backend.domain.medicineboxarea.entity.LocationType;
import com.repill.backend.domain.medicineboxarea.entity.MedicineBoxArea;
import com.repill.backend.domain.medicineboxarea.repostory.MedicineBoxAreaJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MedicineBoxAreaService {

    private final MedicineBoxAreaJpaRepository medicineBoxAreaJpaRepository;

    public List<MedicineBoxAreaResponse.MedicineBoxAreaDetailResponse> getMedicineBoxAreaList(double userLat,
                                                                                              double userLng,
                                                                                              String filter) {
        List<MedicineBoxArea> areas = medicineBoxAreaJpaRepository.findAll();

        // 3km 이내만 필터링
        List<MedicineBoxAreaResponse.MedicineBoxAreaDetailResponse> result = areas.stream()
                .map(area -> MedicineBoxAreaResponse.MedicineBoxAreaDetailResponse.builder()
                        .id(area.getId())
                        .name(area.getName())
                        .latitude(area.getLatitude())
                        .longitude(area.getLongitude())
                        .address(area.getAddress())
                        .telephone(area.getTelephone())
                        .build())
                .filter(area -> calcDistance(userLat, userLng, area.getLatitude(), area.getLongitude()) <= 10.0)
                .collect(Collectors.toList());

        if ("nearest".equals(filter)) {
            result.sort(Comparator.comparingDouble(
                    area -> calcDistance(userLat, userLng, area.getLatitude(), area.getLongitude())));
        } else if ("name".equals(filter)) {
            result.sort(Comparator.comparing(MedicineBoxAreaResponse.MedicineBoxAreaDetailResponse::getName));
        }

        return result;
    }

    public List<MedicineBoxAreaResponse.MedicineBoxAreaDetailResponse> getMedicineBoxAreaListByLocationType(double userLat,
                                                                                                            double userLng,
                                                                                                            String locationTypeStr) {
        LocationType locationType = LocationType.valueOf(locationTypeStr);
        List<MedicineBoxArea> areas = medicineBoxAreaJpaRepository.findAll();

        return areas.stream()
                .filter(area -> area.getLocationType() == locationType)
                .map(area -> MedicineBoxAreaResponse.MedicineBoxAreaDetailResponse.builder()
                        .id(area.getId())
                        .name(area.getName())
                        .latitude(area.getLatitude())
                        .longitude(area.getLongitude())
                        .address(area.getAddress())
                        .telephone(area.getTelephone())
                        .build())
                .filter(area -> calcDistance(userLat, userLng, area.getLatitude(), area.getLongitude()) <= 3.0)
                .collect(Collectors.toList());
    }

    public MedicineBoxAreaResponse.MedicineBoxAreaDetailResponse getMedicineBoxAreaDetail(Long medicineBoxAreaId) {
        MedicineBoxArea area = medicineBoxAreaJpaRepository.findById(medicineBoxAreaId)
                .orElseThrow(() -> new TestHandler(ErrorStatus.MEDICINE_BOX_AREA_NOT_FOUND));

        return MedicineBoxAreaResponse.MedicineBoxAreaDetailResponse.builder()
                .name(area.getName())
                .latitude(area.getLatitude())
                .longitude(area.getLongitude())
                .address(area.getAddress())
                .telephone(area.getTelephone())
                .build();
    }

    // 거리 계산 함수 (Haversine 공식)
    private double calcDistance(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLng / 2) * Math.sin(dLng / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return earthRadius * c;
    }

    private LocationType convertToLocationType(String value) {
        switch (value) {
            case "주민센터":
                return LocationType.COMMUNITY_CENTER;
            case "보건소(지소·분소)":
                return LocationType.HEALTH_CENTER;
            case "복지관":
                return LocationType.WELFARE_CENTER;
            case "지자체 수거소":
                return LocationType.LOCAL_GOV_COLLECTION_CENTER;
            case "구청":
                return LocationType.DISTRICT_OFFICE;
            case "기타":
                return LocationType.ETC;
            default:
                throw new IllegalArgumentException("지원하지 않는 위치 유형입니다.");
        }
    }
}
