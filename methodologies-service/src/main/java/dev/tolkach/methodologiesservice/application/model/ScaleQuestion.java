package dev.tolkach.methodologiesservice.application.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ScaleQuestion {
    private UUID id;
    private UUID scaleId;
    private UUID questionId;
}