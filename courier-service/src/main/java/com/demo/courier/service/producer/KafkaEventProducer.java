package com.demo.courier.service.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaEventProducer {
        private final KafkaTemplate<String, Object> kafkaTemplate;
        public <T> void sendEvent(String topic, T event) {
            kafkaTemplate.send(topic, event);
            log.info("Sent event to topic {}: {}", topic, event.toString());
        }
}
