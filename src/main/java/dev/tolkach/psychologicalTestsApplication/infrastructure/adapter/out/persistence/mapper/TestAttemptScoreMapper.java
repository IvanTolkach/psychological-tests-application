package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.mapper;

import dev.tolkach.psychologicalTestsApplication.domain.model.TestAttemptScore;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.entity.TestAttemptScoreEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TestAttemptScoreMapper {
    TestAttemptScoreEntity toEntity(TestAttemptScore testAttemptScore);
    TestAttemptScore toDomain(TestAttemptScoreEntity entity);
}