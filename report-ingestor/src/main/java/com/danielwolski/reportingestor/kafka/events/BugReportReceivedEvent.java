package com.danielwolski.reportingestor.kafka.events;

import com.danielwolski.reportingestor.reports.dto.BugReportDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BugReportReceivedEvent {
    private String uuid;
    private String summary;
    private String description;
    private String gameVersion;
    private String os;
    private String cpu;
    private String gpu;
    private long ram;
    private List<String> fileUrls;

    public BugReportReceivedEvent(BugReportDto report, List<String> fileUrls) {
        this.uuid =  UUID.randomUUID().toString();
        this.summary = report.summary();
        this.description = report.description();
        this.gameVersion = report.gameVersion();
        this.os = report.os();
        this.cpu = report.cpu();
        this.gpu = report.gpu();
        this.ram = report.ram();
        this.fileUrls = fileUrls;
    }
}
