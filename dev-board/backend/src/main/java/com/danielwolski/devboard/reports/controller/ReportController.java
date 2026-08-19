package com.danielwolski.devboard.reports.controller;

import com.danielwolski.devboard.reports.service.ReportService;
import com.danielwolski.devboard.reports.dto.BugReportDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    public List<BugReportDto> getReports() {
        return reportService.getBugReportDtos();
    }
}
