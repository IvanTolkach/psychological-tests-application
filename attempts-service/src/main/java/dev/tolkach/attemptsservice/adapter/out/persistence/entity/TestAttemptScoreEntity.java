package dev.tolkach.attemptsservice.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "testattemptscore")
@Data
@NoArgsConstructor
public class TestAttemptScoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "test_attempt_id", nullable = false)
    private UUID testAttemptId;

    @Column(name = "scale_id", nullable = false)
    private UUID scaleId;

    @Column(nullable = false)
    private Integer score;
}
