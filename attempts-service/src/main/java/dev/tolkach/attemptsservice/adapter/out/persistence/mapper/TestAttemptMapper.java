package dev.tolkach.attemptsservice.adapter.out.persistence.mapper;

import dev.tolkach.attemptsservice.adapter.out.persistence.entity.TestAttemptEntity;
import dev.tolkach.attemptsservice.application.model.TestAttempt;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TestAttemptMapper {
    TestAttemptEntity toEntity(TestAttempt testAttempt);
    TestAttempt toDomain(TestAttemptEntity entity);
}
