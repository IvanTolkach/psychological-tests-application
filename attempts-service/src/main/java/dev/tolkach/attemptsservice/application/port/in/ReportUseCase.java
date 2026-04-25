package dev.tolkach.attemptsservice.application.port.in;

import dev.tolkach.attemptsservice.application.model.ReportRequest;

public interface ReportUseCase {
    byte[] generateReport(ReportRequest reportRequest);
}
