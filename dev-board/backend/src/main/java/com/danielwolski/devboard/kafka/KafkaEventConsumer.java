package com.danielwolski.devboard.kafka;

import com.danielwolski.devboard.kafka.events.BugReportReceivedEvent;
import com.danielwolski.devboard.reports.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class KafkaEventConsumer {

    private final ReportService reportService;

    @KafkaListener(
            topics = "${app.kafka.topics.bug-reports}",
            properties = {"spring.json.value.default.type=com.danielwolski.devboard.kafka.events.BugReportReceivedEvent"})
    void onBugReportReceived(BugReportReceivedEvent bugReportReceivedEvent){
        log.info("Received event with description {}", bugReportReceivedEvent.getDescription());
        reportService.saveNewBugReportEvent(bugReportReceivedEvent);
    }
}
