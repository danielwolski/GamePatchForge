package com.danielwolski.reportingestor.reports;

import com.danielwolski.reportingestor.reports.dto.BugReport;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping(consumes = {"multipart/form-data"})
    public void reportBug(@RequestPart("report") BugReport report,
                          @RequestPart("files") List<MultipartFile> files) {
        reportService.ingestBugReport(report, files);
    }
}
