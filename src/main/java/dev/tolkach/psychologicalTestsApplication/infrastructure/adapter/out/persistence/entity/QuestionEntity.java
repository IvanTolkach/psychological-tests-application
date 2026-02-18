package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.entity;

import dev.tolkach.psychologicalTestsApplication.domain.model.QuestionType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "question")
@Data
@NoArgsConstructor
public class QuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "test_id", nullable = false)
    private UUID testId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String text;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private QuestionType type;

    @Column(nullable = false)
    private Integer position;
}
