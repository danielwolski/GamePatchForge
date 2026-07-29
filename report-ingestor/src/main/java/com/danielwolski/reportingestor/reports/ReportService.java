package com.danielwolski.reportingestor.reports;

import com.danielwolski.reportingestor.kafka.KafkaEventPublisher;
import com.danielwolski.reportingestor.reports.dto.BugReportDto;
import com.danielwolski.reportingestor.kafka.events.BugReportReceivedEvent;
import com.danielwolski.reportingestor.storage.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportService {

    private final StorageService storageService;
    private final KafkaEventPublisher kafkaEventPublisher;

    public void ingestBugReport(BugReportDto report, List<MultipartFile> files) {
        List<String> fileUrls = files.stream()
                .map(storageService::store)
                .collect(Collectors.toList());

        log.info("Stored {} files for the report", fileUrls.size());

        BugReportReceivedEvent event = new BugReportReceivedEvent(report, fileUrls);
        String reportKey = UUID.randomUUID().toString();

        try {
            kafkaEventPublisher.publishEvent(reportKey, event);
            log.info("Bug report ingestion process initiated with key '{}'", reportKey);
        } catch (Exception e) {
            log.error("Failed to serialize or publish bug report event with key '{}'", reportKey, e);
        }
    }
}
