package dev.tolkach.methodologiesservice.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "scalequestion")
@Data
@NoArgsConstructor
public class ScaleQuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "scale_id", nullable = false)
    private UUID scaleId;

    @Column(name = "question_id", nullable = false)
    private UUID questionId;
}
