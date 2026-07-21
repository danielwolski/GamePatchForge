package com.danielwolski.reportingestor.reports.events;

import com.danielwolski.reportingestor.reports.dto.BugReport;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BugReportEvent {
    private String summary;
    private String description;
    private String gameVersion;
    private String os;
    private String cpu;
    private String gpu;
    private long ram;
    private List<String> fileUrls;

    public BugReportEvent(BugReport report, List<String> fileUrls) {
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
