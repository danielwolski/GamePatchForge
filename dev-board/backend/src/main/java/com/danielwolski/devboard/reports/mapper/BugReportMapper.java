package com.danielwolski.devboard.reports.mapper;

import com.danielwolski.devboard.reports.dto.BugReportDetailsDto;
import com.danielwolski.devboard.reports.dto.BugReportDto;
import com.danielwolski.devboard.kafka.events.BugReportReceivedEvent;
import com.danielwolski.devboard.reports.model.BugReport;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BugReportMapper {
    BugReportDto bugReportToBugReportDto(BugReport bugReport);
    BugReportDetailsDto bugReportToBugReportDetailsDto(BugReport bugReport);

    BugReport bugReportReceivedEventToBugReport(BugReportReceivedEvent bugReportReceivedEvent);
}
