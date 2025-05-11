package com.demo.courier.service;

import com.demo.courier.configuration.properties.KafkaProperties;
import com.demo.courier.entity.Courier;
import com.demo.courier.exception.CourierRuntimeException;
import com.demo.courier.mapper.CourierMapper;
import com.demo.courier.model.enums.CourierStatus;
import com.demo.courier.model.request.CourierCreateRequest;
import com.demo.courier.repository.CourierRepository;
import com.demo.courier.service.producer.KafkaEventProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CourierServiceTest {

    @Mock
    private CourierTrackService courierTrackService;

    @Mock
    private CourierRepository courierRepository;

    @Mock
    private KafkaEventProducer kafkaEventProducer;

    @Mock
    private KafkaProperties kafkaProperties;

    @Mock
    private CourierMapper courierMapper;

    @InjectMocks
    private CourierService courierService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCourier_shouldSaveCourier_whenIdentityNoDoesNotExist() {
        CourierCreateRequest request = mock(CourierCreateRequest.class);
        when(request.identityNo()).thenReturn("123456789");
        when(courierRepository.existsByIdentityNo("123456789")).thenReturn(false);

        Courier courier = new Courier();
        courier.setFirstName("John");
        courier.setLastName("Doe");
        when(courierMapper.createCourierRequestToCourier(request)).thenReturn(courier);

        courierService.createCourier(request);

        verify(courierRepository).save(courier);
        verify(courierMapper).createCourierRequestToCourier(request);
    }

    @Test
    void createCourier_shouldThrowException_whenIdentityNoExists() {
        CourierCreateRequest request = mock(CourierCreateRequest.class);
        when(request.identityNo()).thenReturn("123456789");
        when(courierRepository.existsByIdentityNo("123456789")).thenReturn(true);

        assertThrows(CourierRuntimeException.class, () -> courierService.createCourier(request));
        verify(courierRepository, never()).save(any());
    }
}
