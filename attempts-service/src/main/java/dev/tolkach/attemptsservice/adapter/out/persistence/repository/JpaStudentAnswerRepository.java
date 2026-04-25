package dev.tolkach.attemptsservice.adapter.out.persistence.repository;

import dev.tolkach.attemptsservice.adapter.out.persistence.entity.StudentAnswerEntity;
import dev.tolkach.attemptsservice.application.model.ScaleScoreResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaStudentAnswerRepository extends JpaRepository<StudentAnswerEntity, UUID>, JpaSpecificationExecutor<StudentAnswerEntity> {
    @Query(value = """
        WITH scale_scores AS (
            SELECT
                sq.scale_id,
                SUM(
                    COALESCE(
                        ao.score,
                        NULLIF(sa.answer_value,'')::INTEGER,
                        0
                    )
                ) AS score
            FROM attempts.studentanswer sa
            LEFT JOIN tests.answeroption ao ON ao.id = sa.answer_option_id
            JOIN methodologies.scalequestion sq ON sq.question_id = sa.question_id
            WHERE sa.test_attempt_id = :attemptId
            GROUP BY sq.scale_id
        ),
    
        total_score AS (
             SELECT
                 s.id AS scale_id,
                 SUM(
                     COALESCE(
                         ao.score,
                         NULLIF(sa.answer_value,'')::INTEGER,
                         0
                     )
                 ) AS score
             FROM attempts.testattempt ta
             JOIN tests.test t ON t.id = ta.test_id
             JOIN methodologies.scale s\s
                 ON s.methodology_id = t.methodology_id
             JOIN attempts.studentanswer sa\s
                 ON sa.test_attempt_id = ta.id
             LEFT JOIN tests.answeroption ao\s
                 ON ao.id = sa.answer_option_id
             WHERE ta.id = :attemptId
               AND s.is_total = true
             GROUP BY s.id
         )
    
        SELECT
            ss.scale_id AS scaleId,
            ss.score AS score,
            sr.interpretation AS interpretation
        FROM (
            SELECT * FROM scale_scores
            UNION ALL
            SELECT * FROM total_score ts
            WHERE NOT EXISTS (
                SELECT 1
                FROM scale_scores ss
                WHERE ss.scale_id = ts.scale_id
            )
        ) ss
        LEFT JOIN methodologies.scorerange sr
            ON sr.scale_id = ss.scale_id
            AND ss.score BETWEEN sr.min_score AND sr.max_score;
    """, nativeQuery = true)
    List<ScaleScoreResult> calculateScoresWithInterpretation(UUID attemptId);
}
