package com.danielwolski.devboard.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
class KafkaEventConsumer {

    @KafkaListener(topics = "${app.kafka.topics.reports}")
    void onReportReceived(String payload){
        log.info("Received: {}", payload);
    }
}
