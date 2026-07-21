package com.danielwolski.reportingestor.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaEventPublisher {

    private static final String REPORTS_TOPIC = "reports";
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void publishEvent(String message) {
        kafkaTemplate.send(REPORTS_TOPIC, message);
        log.info("Sending event to Kafka: {}", message);
    }
}
