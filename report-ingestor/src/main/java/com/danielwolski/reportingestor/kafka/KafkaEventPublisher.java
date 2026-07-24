package com.danielwolski.reportingestor.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaEventPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${app.kafka.topics.reports}")
    private String reportsTopic;

    public void publishEvent(String key, String message) {
        log.debug("Initiating event send to topic '{}' with key '{}'", reportsTopic, key);

        kafkaTemplate.send(reportsTopic, key, message)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Event sent successfully to topic '{}', partition {}, offset {}, key '{}'",
                                reportsTopic,
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset(),
                                key);
                    } else {
                        log.error("Failed to send event to topic '{}' with key '{}'", reportsTopic, key, ex);
                    }
                });
    }
}
