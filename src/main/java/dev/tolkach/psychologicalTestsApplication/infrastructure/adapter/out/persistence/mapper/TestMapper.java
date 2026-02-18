package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.mapper;

import dev.tolkach.psychologicalTestsApplication.domain.model.Test;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.entity.TestEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TestMapper {
    TestEntity toEntity(Test test);
    Test toDomain(TestEntity entity);
}
