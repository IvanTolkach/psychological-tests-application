package dev.tolkach.testsservice.application.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class TestFilter {
    private UUID id;
    private String name;
    private String introduction;
    private UUID methodologyId;
    private Boolean isActive;
    private UUID createdBy;
    private LocalDateTime createdAtFrom;
    private LocalDateTime createdAtTo;
    private UUID updatedBy;
    private LocalDateTime updatedAtFrom;
    private LocalDateTime updatedAtTo;
}
