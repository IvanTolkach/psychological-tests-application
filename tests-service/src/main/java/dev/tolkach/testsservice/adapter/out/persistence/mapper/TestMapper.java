package dev.tolkach.testsservice.adapter.out.persistence.mapper;

import dev.tolkach.testsservice.adapter.out.persistence.entity.TestEntity;
import dev.tolkach.testsservice.application.model.Test;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TestMapper {
    TestEntity toEntity(Test test);
    Test toDomain(TestEntity entity);
}
