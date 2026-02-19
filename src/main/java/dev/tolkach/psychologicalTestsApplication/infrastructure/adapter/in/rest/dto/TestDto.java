package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class TestDto {
    private UUID id;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 100, message = "Name must be at most 100 characters")
    private String name;

    @NotNull(message = "Methodology ID is required")
    private UUID methodologyId;

    private Boolean isActive;

    //TODO сделать автоматическую подстановку
    @NotNull(message = "Created by admin ID is required")
    private UUID createdBy;

    private LocalDateTime createdAt;

    private LocalDateTime createdAtFrom;
    private LocalDateTime createdAtTo;

    //TODO сделать автоматическую подстановку
    private UUID updatedBy;

    private LocalDateTime updatedAt;

    private LocalDateTime updatedAtFrom;
    private LocalDateTime updatedAtTo;
}
