package dev.tolkach.psychologicalTestsApplication.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class Scale {
    private UUID id;
    private UUID methodologyId;
    private String name;
    private Boolean isTotal;
    private String description;
}
