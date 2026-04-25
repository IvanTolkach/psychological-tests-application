package dev.tolkach.methodologiesservice.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "scorerange")
@Data
@NoArgsConstructor
public class ScoreRangeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "scale_id", nullable = false)
    private UUID scaleId;

    @Column(name = "min_score", nullable = false)
    private Integer minScore;

    @Column(name = "max_score", nullable = false)
    private Integer maxScore;

    @Column(nullable = false, length = 50)
    private String interpretation;

    @Column(columnDefinition = "TEXT")
    private String description;
}
