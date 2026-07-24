package com.danielwolski.reportingestor.reports;

import com.danielwolski.reportingestor.kafka.KafkaEventPublisher;
import com.danielwolski.reportingestor.reports.dto.BugReport;
import com.danielwolski.reportingestor.reports.events.BugReportEvent;
import com.danielwolski.reportingestor.storage.StorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private StorageService storageService;

    @Mock
    private KafkaEventPublisher kafkaEventPublisher;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private ReportService reportService;

    @BeforeEach
    void setUp() {
        reportService = new ReportService(storageService, kafkaEventPublisher, objectMapper);
    }

    @Test
    void ingestBugReport_shouldStoreFilesAndPublishEventWithKey() throws Exception {
        // Given
        var bugReportDto = new BugReport("Crash on startup", "Game crashes", "1.0", "Win10", "i7", "RTX 3080", 16L);
        List<MultipartFile> files = List.of(
                new MockMultipartFile("log", "log.txt", "text/plain", "log content".getBytes()),
                new MockMultipartFile("screenshot", "screenshot.png", "image/png", "image content".getBytes())
        );

        String expectedLogUrl = "http://storage/log.txt";
        String expectedImageUrl = "http://storage/screenshot.png";
        when(storageService.store(any(MultipartFile.class))).thenReturn(expectedLogUrl, expectedImageUrl);

        ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> eventCaptor = ArgumentCaptor.forClass(String.class);

        // When
        reportService.ingestBugReport(bugReportDto, files);

        // Then
        verify(storageService, times(2)).store(any(MultipartFile.class));
        verify(kafkaEventPublisher, times(1)).publishEvent(keyCaptor.capture(), eventCaptor.capture());

        String publishedKey = keyCaptor.getValue();
        assertThat(publishedKey).isNotNull().isNotEmpty();

        String publishedEventJson = eventCaptor.getValue();
        BugReportEvent publishedEvent = objectMapper.readValue(publishedEventJson, BugReportEvent.class);

        assertThat(publishedEvent.getSummary()).isEqualTo(bugReportDto.summary());
        assertThat(publishedEvent.getFileUrls()).containsExactlyInAnyOrder(expectedLogUrl, expectedImageUrl);
    }

    @Test
    void ingestBugReport_shouldNotPublishEventWhenSerializationFails() throws JsonProcessingException {
        // Given
        var bugReportDto = new BugReport("Test", "Test", "1.0", "OS", "CPU", "GPU", 8L);
        List<MultipartFile> files = List.of(new MockMultipartFile("file", "file.txt", "text/plain", "content".getBytes()));

        ObjectMapper failingMapper = mock(ObjectMapper.class);
        when(failingMapper.writeValueAsString(any())).thenThrow(new JsonProcessingException("Serialization failed") {});

        reportService = new ReportService(storageService, kafkaEventPublisher, failingMapper);

        // When
        reportService.ingestBugReport(bugReportDto, files);

        // Then
        verify(kafkaEventPublisher, never()).publishEvent(anyString(), anyString());
    }
}
