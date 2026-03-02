package dev.tolkach.methodologiesservice.adapter.out.persistence.repository;

import dev.tolkach.methodologiesservice.adapter.out.persistence.entity.ScoreRangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaScoreRangeRepository extends JpaRepository<ScoreRangeEntity, UUID>, JpaSpecificationExecutor<ScoreRangeEntity> {
}
