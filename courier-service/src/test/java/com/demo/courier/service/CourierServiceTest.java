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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        // Arrange
        CourierCreateRequest request = mock(CourierCreateRequest.class);
        when(request.identityNo()).thenReturn("123456789");
        when(courierRepository.existsByIdentityNo("123456789")).thenReturn(true);

        // Act & Assert
        assertThrows(CourierRuntimeException.class, () -> courierService.createCourier(request));
        verify(courierRepository, never()).save(any());
    }

    @Test
    void getCourier_shouldReturnCourierDto_whenCourierExists() {
        // Arrange
        Long courierId = 1L;
        Courier courier = new Courier();
        courier.setId(courierId);
        courier.setFirstName("John");
        courier.setLastName("Doe");

        CourierDto courierDto = new CourierDto();
        courierDto.setFirstName("John");
        courierDto.setLastName("Doe");

        when(courierRepository.findById(courierId)).thenReturn(Optional.of(courier));
        when(courierMapper.courierToCourierDto(courier)).thenReturn(courierDto);

        // Act
        CourierDto result = courierService.getCourier(courierId);

        // Assert
        assertEquals(courierDto, result);
        verify(courierRepository, times(1)).findById(courierId);
        verify(courierMapper, times(1)).courierToCourierDto(courier);
    }

    @Test
    void saveCurrentLocation_shouldSaveLocation_whenCourierExists() {
        // Arrange
        CourierLocationRequest request = new CourierLocationRequest();
        request.setCourierId(1L);
        request.setLatitude(40.7128);
        request.setLongitude(-74.0060);

        Courier courier = new Courier();
        courier.setId(1L);

        when(courierRepository.findById(1L)).thenReturn(Optional.of(courier));

        courierService.saveCurrentLocation(request);

        verify(courierRepository, times(1)).findById(1L);
        verify(courierTrackService, times(1)).saveCourierTrack(request);
    }

    @Test
    void saveCurrentLocation_shouldThrowException_whenCourierDoesNotExist() {
        // Arrange
        CourierLocationRequest request = new CourierLocationRequest();
        request.setCourierId(1L);

        when(courierRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CourierRuntimeException.class, () -> courierService.saveCurrentLocation(request));
        verify(courierRepository).findById(1L);
        verifyNoInteractions(courierTrackService);
    }

    @Test
    void updateCourierStatus_shouldUpdateStatus_whenCourierExists() {
        Long courierId = 1L;
        CourierStatus newStatus = CourierStatus.NOT_WORKING;

        Courier courier = new Courier();
        courier.setId(courierId);
        courier.setStatus(CourierStatus.AVAILABLE);

        when(courierRepository.findById(courierId)).thenReturn(Optional.of(courier));

        courierService.updateCourierStatus(courierId, newStatus);

        verify(courierRepository, times(1)).findById(courierId);
        verify(courierRepository, times(1)).save(courier);
        assert courier.getStatus() == newStatus;
    }

    @Test
    void updateCourierStatus_shouldThrowException_whenCourierDoesNotExist() {
        Long courierId = 1L;
        CourierStatus newStatus = CourierStatus.NOT_WORKING;

        when(courierRepository.findById(courierId)).thenReturn(Optional.empty());

        assertThrows(CourierRuntimeException.class, () -> courierService.updateCourierStatus(courierId, newStatus));
        verify(courierRepository).findById(courierId);
        verifyNoMoreInteractions(courierRepository);
    }
}
