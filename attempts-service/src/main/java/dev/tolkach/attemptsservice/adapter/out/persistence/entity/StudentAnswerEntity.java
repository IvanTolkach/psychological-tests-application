package dev.tolkach.attemptsservice.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "studentanswer")
@Data
@NoArgsConstructor
public class StudentAnswerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "test_attempt_id", nullable = false)
    private UUID testAttemptId;

    @Column(name = "question_id", nullable = false)
    private UUID questionId;

    @Column(name = "answer_option_id")
    private UUID answerOptionId;

    @Column(name = "answer_value", columnDefinition = "TEXT")
    private String answerValue;
}
