package com.danielwolski.devboard.reports.repository;

import com.danielwolski.devboard.reports.model.BugReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<BugReport, String> {
}
