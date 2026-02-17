package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "scale")
@Data
@NoArgsConstructor
public class ScaleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "methodology_id", nullable = false)
    private UUID methodologyId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "is_total", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isTotal;

    @Column(columnDefinition = "TEXT")
    private String description;
}
