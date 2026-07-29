package com.danielwolski.devboard.reports.dto;

public record BugReportDto(
        String uuid,
        String summary,
        String description,
        String gameVersion,
        String os,
        String cpu,
        String gpu,
        long ram
) {
}
