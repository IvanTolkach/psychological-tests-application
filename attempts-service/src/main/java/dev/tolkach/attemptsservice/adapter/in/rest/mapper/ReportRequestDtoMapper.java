package dev.tolkach.attemptsservice.adapter.in.rest.mapper;

import dev.tolkach.attemptsservice.adapter.in.rest.dto.ReportRequestDto;
import dev.tolkach.attemptsservice.application.model.ReportRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReportRequestDtoMapper {
    ReportRequest toEntity(ReportRequestDto dto);
    ReportRequestDto toDto(ReportRequest entity);
}
