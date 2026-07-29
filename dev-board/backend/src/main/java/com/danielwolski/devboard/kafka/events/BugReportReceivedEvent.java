package com.danielwolski.devboard.kafka.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
public class BugReportReceivedEvent {
    private String summary;
    private String description;
    private String gameVersion;
    private String os;
    private String cpu;
    private String gpu;
    private long ram;
    private List<String> fileUrls;
}
