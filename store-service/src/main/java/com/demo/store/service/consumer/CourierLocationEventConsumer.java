package com.demo.store.service.consumer;

import com.demo.core.event.CourierLocationEvent;
import com.demo.store.service.StoreEntryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourierLocationEventConsumer {

    private final StoreEntryService storeEntryService;

    @KafkaListener(topics = "courier-location", containerFactory = "kafkaListenerContainerFactory")
    public void listenCourierLocation(CourierLocationEvent event) {
        log.info("Consumed courier location for store {}", event.toString());
        storeEntryService.courierForStore(event);
    }
}
