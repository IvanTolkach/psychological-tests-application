package dev.tolkach.attemptsservice.adapter.out.persistence.mapper;

import dev.tolkach.attemptsservice.adapter.out.persistence.entity.TestAttemptScoreEntity;
import dev.tolkach.attemptsservice.application.model.TestAttemptScore;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TestAttemptScoreMapper {
    TestAttemptScoreEntity toEntity(TestAttemptScore testAttemptScore);
    TestAttemptScore toDomain(TestAttemptScoreEntity entity);
}