package dev.tolkach.testsservice.adapter.out.persistence.repository;

import dev.tolkach.testsservice.adapter.out.persistence.entity.AnswerOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaAnswerOptionRepository extends JpaRepository<AnswerOptionEntity, UUID>, JpaSpecificationExecutor<AnswerOptionEntity> {
}
