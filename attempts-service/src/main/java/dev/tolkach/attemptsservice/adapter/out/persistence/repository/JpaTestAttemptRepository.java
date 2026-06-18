package dev.tolkach.attemptsservice.adapter.out.persistence.repository;

import dev.tolkach.attemptsservice.adapter.out.persistence.entity.TestAttemptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface JpaTestAttemptRepository extends JpaRepository<TestAttemptEntity, UUID>, JpaSpecificationExecutor<TestAttemptEntity> {
    boolean existsByStudentIdAndTestIdAndAttemptDateGreaterThanEqualAndAttemptDateLessThan(
            UUID studentId,
            UUID testId,
            LocalDateTime fromInclusive,
            LocalDateTime toExclusive
    );

    boolean existsByStudentIdAndTestIdAndAttemptDateGreaterThanEqualAndAttemptDateLessThanAndIdNot(
            UUID studentId,
            UUID testId,
            LocalDateTime fromInclusive,
            LocalDateTime toExclusive,
            UUID excludedId
    );
}
