package com.demo.courier.service;

import com.demo.core.enums.CourierStatus;
import com.demo.courier.configuration.properties.KafkaProperties;
import com.demo.courier.entity.Courier;
import com.demo.courier.exception.CourierRuntimeException;
import com.demo.courier.mapper.CourierMapper;
import com.demo.courier.model.dto.CourierDto;
import com.demo.courier.model.request.CourierCreateRequest;
import com.demo.courier.model.request.CourierLocationRequest;
import com.demo.courier.repository.CourierRepository;
import com.demo.courier.service.producer.KafkaEventProducer;
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

    private final KafkaEventProducer kafkaEventProducer;
    private final KafkaProperties kafkaProperties;

    private final CourierMapper courierMapper;

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
                .orElseThrow(() -> new CourierRuntimeException("Courier is not found"));
        return courierMapper.courierToCourierDto(courier);
    }

    @Transactional
    public void saveCurrentLocation(CourierLocationRequest request) {
        courierRepository.findById(request.getCourierId())
                .orElseThrow(() -> new CourierRuntimeException("Courier is not found"));
        courierTrackService.saveCourierTrack(request);
        log.info("Courier current location was saved. CourierId: {}", request.getCourierId());
    }

    public void updateCourierStatus(Long id, CourierStatus status) {
        log.info("Courier status update request was received for CourierId : {}", id);
        Courier courier = courierRepository.findById(id)
                .orElseThrow(() -> new CourierRuntimeException("Courier is not found"));

        courier.setStatus(status);
        courierRepository.save(courier);
    }
}
