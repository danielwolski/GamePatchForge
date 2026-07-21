package com.danielwolski.reportingestor.reports.dto;

public record BugReport(
    String summary,
    String description,
    String gameVersion,
    String os,
    String cpu,
    String gpu,
    long ram
) {}
