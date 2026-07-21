package com.danielwolski.reportingestor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class ReportIngestorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportIngestorApplication.class, args);
		log.info("Report Ingestor started");
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
}
