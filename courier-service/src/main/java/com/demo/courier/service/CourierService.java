package com.demo.courier.service;

import com.demo.courier.entity.Courier;
import com.demo.courier.exception.CourierRuntimeException;
import com.demo.courier.mapper.CourierMapper;
import com.demo.courier.model.dto.CourierDto;
import com.demo.courier.model.request.CourierCreateRequest;
import com.demo.courier.model.enums.CourierStatus;
import com.demo.courier.model.request.CourierLocationRequest;
import com.demo.courier.repository.CourierRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class CourierService {

    private final CourierTrackService courierTrackService;
    private final CourierRepository courierRepository;

    private CourierMapper courierMapper;

    public void createCourier(CourierCreateRequest request) {

        if (courierRepository.existsByIdentityNo(request.identityNo())) {
            throw new CourierRuntimeException("Already exist courier with this identity no");
        }

        Courier courier = courierMapper.createCourierRequestToCourier(request);
        courier.setStatus(CourierStatus.AVAILABLE);
        courierRepository.save(courier);

        log.info("Created Courier: {} {}", courier.getFirstName(), courier.getLastName());
    }

    public CourierDto getCourier(Long id) {
        Courier courier = courierRepository.findById(id)
                .orElseThrow(() -> new CourierRuntimeException("COURIER_IS_NOT_FOUND"));
        return courierMapper.courierToCourierDto(courier);
    }

    @Transactional
    public void saveCurrentLocation(CourierLocationRequest request) {

        courierRepository.findById(request.courierId())
                .orElseThrow(() -> new CourierRuntimeException("COURIER_IS_NOT_FOUND"));

        courierTrackService.saveCourierTrack(request);
        log.info("Courier track saved");
    }

    public void updateCourierStatus(Long id, CourierStatus status) {
    }
}
