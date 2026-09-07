package com.danielwolski.devboard.reports.dto;

import java.util.List;

public record BugReportDetailsDto(
        String uuid,
        String summary,
        String description,
        String gameVersion,
        String os,
        String cpu,
        String gpu,
        long ram,
        List<String> fileUrls
) {
}
