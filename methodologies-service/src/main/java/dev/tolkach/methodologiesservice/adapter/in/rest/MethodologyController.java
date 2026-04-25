package dev.tolkach.methodologiesservice.adapter.in.rest;

import dev.tolkach.methodologiesservice.adapter.in.rest.dto.MethodologyDto;
import dev.tolkach.methodologiesservice.adapter.in.rest.endpoint.MethodologyEndpoint;
import dev.tolkach.methodologiesservice.adapter.in.rest.mapper.MethodologyDtoMapper;
import dev.tolkach.methodologiesservice.application.model.Methodology;
import dev.tolkach.methodologiesservice.application.port.in.MethodologyUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class MethodologyController implements MethodologyEndpoint {

    private final MethodologyUseCase methodologyUseCase;
    private final MethodologyDtoMapper methodologyDtoMapper;

    public MethodologyController(MethodologyUseCase methodologyUseCase, MethodologyDtoMapper methodologyDtoMapper) {
        this.methodologyUseCase = methodologyUseCase;
        this.methodologyDtoMapper = methodologyDtoMapper;
    }

    @Override
    public ResponseEntity<List<MethodologyDto>> getMethodologies(MethodologyDto filter) {
        Methodology methodologyFilter = methodologyDtoMapper.toEntity(filter);
        List<Methodology> methodologies = methodologyUseCase.getMethodologiesByFilter(methodologyFilter);
        List<MethodologyDto> responseDtos = methodologies.stream().map(methodologyDtoMapper::toDto).toList();
        return ResponseEntity.ok(responseDtos);
    }

    @Override
    public ResponseEntity<MethodologyDto> getMethodologyById(UUID methodologyId) {
        Methodology methodology = methodologyUseCase.getMethodologyById(methodologyId);
        MethodologyDto responseDto = methodologyDtoMapper.toDto(methodology);
        return ResponseEntity.ok(responseDto);
    }

    @Override
    public ResponseEntity<MethodologyDto> createUpdateMethodology(MethodologyDto dto) {
        Methodology methodology = methodologyDtoMapper.toEntity(dto);
        Methodology saved = methodologyUseCase.createUpdateMethodology(methodology);
        MethodologyDto responseDto = methodologyDtoMapper.toDto(saved);
        if (dto.getId() == null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } else {
            return ResponseEntity.ok(responseDto);
        }
    }

    @Override
    public ResponseEntity<Void> deleteMethodology(UUID methodologyId) {
        methodologyUseCase.deleteMethodology(methodologyId);
        return ResponseEntity.noContent().build();
    }
}
