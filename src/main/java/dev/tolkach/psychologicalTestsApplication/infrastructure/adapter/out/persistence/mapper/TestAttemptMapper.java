package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.mapper;

import dev.tolkach.psychologicalTestsApplication.domain.model.TestAttempt;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.entity.TestAttemptEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TestAttemptMapper {
    TestAttemptEntity toEntity(TestAttempt testAttempt);
    TestAttempt toDomain(TestAttemptEntity entity);
}
