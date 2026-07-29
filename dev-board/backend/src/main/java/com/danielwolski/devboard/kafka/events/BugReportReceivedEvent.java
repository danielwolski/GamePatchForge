package com.danielwolski.devboard.kafka.events;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BugReportReceivedEvent {
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
