package com.demo.courier.service;

import com.demo.core.event.CourierLocationEvent;
import com.demo.courier.configuration.properties.KafkaProperties;
import com.demo.courier.entity.CourierTrack;
import com.demo.courier.mapper.CourierTrackMapper;
import com.demo.courier.model.request.CourierLocationRequest;
import com.demo.courier.repository.CourierTrackRepository;
import com.demo.courier.service.producer.KafkaEventProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CourierTrackServiceTest {

    @Mock
    private CourierTrackRepository courierTrackRepository;

    @Mock
    private CourierTrackMapper courierTrackMapper;

    @Mock
    private KafkaEventProducer kafkaEventProducer;

    @Mock
    private KafkaProperties kafkaProperties;

    @InjectMocks
    private CourierTrackService courierTrackService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveCourierTrack_shouldSaveTrackAndSendEvent() {
        CourierLocationRequest request = new CourierLocationRequest();
        request.setCourierId(1L);
        request.setLatitude(40.7128);
        request.setLongitude(-74.0060);
        KafkaProperties.Topic topic = new KafkaProperties.Topic();
        topic.setCourierLocation("courier-location-topic");
        kafkaProperties.setTopic(topic);
        CourierTrack courierTrack = new CourierTrack();
        CourierLocationEvent event = new CourierLocationEvent();

        when(courierTrackMapper.locationRequestToCourierTrack(request)).thenReturn(courierTrack);
        when(courierTrackRepository.save(courierTrack)).thenReturn(courierTrack);
        when(courierTrackMapper.courierTrackToCourierLocationEvent(courierTrack)).thenReturn(event);
        when(kafkaProperties.getTopic()).thenReturn(topic);

        courierTrackService.saveCourierTrack(request);

        verify(courierTrackMapper).locationRequestToCourierTrack(request);
        verify(courierTrackRepository).save(courierTrack);
        verify(courierTrackMapper).courierTrackToCourierLocationEvent(courierTrack);
        verify(kafkaEventProducer).sendEvent("courier-location-topic", event);
    }
}
