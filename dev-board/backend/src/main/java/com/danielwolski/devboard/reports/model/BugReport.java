package com.danielwolski.devboard.reports.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class BugReport {
    @Id
    private String uuid;
    private String summary;
    private String description;
    private String gameVersion;
    private String os;
    private String cpu;
    private String gpu;
    private long ram;
    private List<String> fileUrls;
}

