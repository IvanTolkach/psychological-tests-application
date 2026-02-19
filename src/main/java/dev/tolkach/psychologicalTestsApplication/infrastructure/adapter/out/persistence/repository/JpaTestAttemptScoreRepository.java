package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.repository;

import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.entity.TestAttemptScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaTestAttemptScoreRepository extends JpaRepository<TestAttemptScoreEntity, UUID>, JpaSpecificationExecutor<TestAttemptScoreEntity> {
}
