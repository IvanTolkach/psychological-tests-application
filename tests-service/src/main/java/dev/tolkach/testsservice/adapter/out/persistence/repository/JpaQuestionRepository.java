package dev.tolkach.testsservice.adapter.out.persistence.repository;

import dev.tolkach.testsservice.adapter.out.persistence.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaQuestionRepository extends JpaRepository<QuestionEntity, UUID>, JpaSpecificationExecutor<QuestionEntity> {
    @Modifying
    @Query("""
        UPDATE QuestionEntity q
        SET q.position = q.position + 1
        WHERE q.testId = :testId
        AND q.position >= :position
    """)
    void shiftDown(UUID testId, int position);

    @Modifying
    @Query("""
        UPDATE QuestionEntity q
        SET q.position = q.position - 1
        WHERE q.testId = :testId
        AND q.position > :position
    """)
    void shiftUp(UUID testId, int position);

    @Modifying
    @Query("""
        UPDATE QuestionEntity q
        SET q.position = q.position + 1
        WHERE q.testId = :testId
        AND q.position >= :newPos
        AND q.position < :oldPos
    """)
    void shiftForMoveUp(UUID testId, int newPos, int oldPos);

    @Modifying
    @Query("""
        UPDATE QuestionEntity q
        SET q.position = q.position - 1
        WHERE q.testId = :testId
        AND q.position > :oldPos
        AND q.position <= :newPos
    """)
    void shiftForMoveDown(UUID testId, int oldPos, int newPos);

    @Query("""
        SELECT COALESCE(MAX(q.position),0)
        FROM QuestionEntity q
        WHERE q.testId = :testId
    """)
    int getMaxPosition(UUID testId);
}
