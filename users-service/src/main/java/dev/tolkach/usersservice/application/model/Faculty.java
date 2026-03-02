package dev.tolkach.usersservice.application.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class Faculty {
    private UUID id;
    private String name;
}