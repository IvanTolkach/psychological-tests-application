package dev.tolkach.methodologiesservice.adapter.out.persistence.repository;

import dev.tolkach.methodologiesservice.adapter.out.persistence.entity.ScaleQuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaScaleQuestionRepository extends JpaRepository<ScaleQuestionEntity, UUID>, JpaSpecificationExecutor<ScaleQuestionEntity> {
}
