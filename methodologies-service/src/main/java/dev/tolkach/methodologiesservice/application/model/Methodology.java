package dev.tolkach.methodologiesservice.application.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class Methodology {
    private UUID id;
    private String name;
    private String description;
}
