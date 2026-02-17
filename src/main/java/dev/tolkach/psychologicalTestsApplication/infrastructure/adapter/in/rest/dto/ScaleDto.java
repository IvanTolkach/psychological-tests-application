package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ScaleDto {
    private UUID id;

    @NotNull(message = "Methodology ID is required")
    private UUID methodologyId;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 100, message = "Name must be at most 100 characters")
    private String name;

    private Boolean isTotal;

    @Size(max = 2000, message = "Description must be at most 2000 characters")
    private String description;
}
