# GamePatchForge
A telemetry and bug-tracking system designed for game developers to asynchronously gather in-game bug reports, aggregate player hardware data, and track the statuses of bug tickets.

## Overview
## Overview
A player reports a bug through an in-game form. The payload, 
containing game state metrics and binary data (logs and screenshots), 
is sent via a multipart request to the ingestion endpoint:

```http
POST /api/v1/reports
```

Microservices:

1. Report Ingestor: Acts as a lightweight, stateless router. It handles the incoming request, 
offloads the heavy binary files directly to Cloud Storage, and publishes a BugReportReceived event to Apache Kafka.


2. DevBoard: Consumes the event from Kafka and persists the normalized data 
into its own database.
Through a dedicated web dashboard, game developers can review 
incoming raw reports, analyze aggregated hardware telemetry, and promote specific 
bug reports to actionable tickets, which are then tracked in a built-in Jira-style workflow manager
