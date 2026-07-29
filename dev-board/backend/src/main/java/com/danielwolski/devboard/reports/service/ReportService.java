package com.danielwolski.devboard.reports.service;

import com.danielwolski.devboard.kafka.events.BugReportReceivedEvent;
import com.danielwolski.devboard.reports.dto.BugReportDto;
import com.danielwolski.devboard.reports.mapper.BugReportMapper;
import com.danielwolski.devboard.reports.model.BugReport;
import com.danielwolski.devboard.reports.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final BugReportMapper bugReportMapper;

    public List<BugReportDto> getBugReportDtos() {
        List<BugReport> bugReportList = reportRepository.findAll();
        List<BugReportDto> bugReportDtoList = bugReportList.stream().map(bugReportMapper::bugReportToBugReportDto).toList();
        log.info("Returning {} bugReportDtos", bugReportDtoList.size());
        return bugReportDtoList;
    }

    public void saveNewBugReportEvent(BugReportReceivedEvent bugReportReceivedEvent) {
        BugReport bugReport = bugReportMapper.bugReportReceivedEventToBugReport(bugReportReceivedEvent);
        try {
            reportRepository.save(bugReport);
            log.info("Saved new bug report");
        } catch (Exception e) {
            log.warn("Error saving {} ", e.getMessage());
        }
    }
}
