package com.danielwolski.reportingestor.reports;

import com.danielwolski.reportingestor.kafka.KafkaEventPublisher;
import com.danielwolski.reportingestor.reports.dto.BugReport;
import com.danielwolski.reportingestor.reports.events.BugReportEvent;
import com.danielwolski.reportingestor.storage.StorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportService {

    private final StorageService storageService;
    private final KafkaEventPublisher kafkaEventPublisher;
    private final ObjectMapper objectMapper;

    public void ingestBugReport(BugReport report, List<MultipartFile> files) {
        try {
            String eventJson = getBugReportEventJson(report, files);
            kafkaEventPublisher.publishEvent(eventJson);
            log.info("BugReport ingested successfully");
        } catch (Exception e) {
            log.error("Failed to ingest BugReport", e);
        }
    }

    private String getBugReportEventJson(BugReport report, List<MultipartFile> files) throws JsonProcessingException {
        List<String> fileUrls = storeFilesAndGetUrls(files);
        BugReportEvent event = new BugReportEvent(report, fileUrls);
        return objectMapper.writeValueAsString(event);
    }

    private List<String> storeFilesAndGetUrls(List<MultipartFile> files) {
        return files.stream()
                .map(storageService::store)
                .toList();
    }
}
