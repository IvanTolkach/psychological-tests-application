package dev.tolkach.attemptsservice.adapter.out.persistence.repository;

import dev.tolkach.attemptsservice.adapter.out.persistence.entity.StudentAnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface JpaStudentAnswerRepository extends JpaRepository<StudentAnswerEntity, UUID>, JpaSpecificationExecutor<StudentAnswerEntity> {
    List<StudentAnswerEntity> findByTestAttemptIdIn(Collection<UUID> attemptIds);
}
