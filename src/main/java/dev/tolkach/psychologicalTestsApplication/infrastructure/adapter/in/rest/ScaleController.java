package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest;

import dev.tolkach.psychologicalTestsApplication.domain.model.Scale;
import dev.tolkach.psychologicalTestsApplication.domain.port.in.ScaleUseCase;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.dto.ScaleDto;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.endpoint.ScaleEndpoint;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.mapper.ScaleDtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class ScaleController implements ScaleEndpoint {

    private final ScaleUseCase scaleUseCase;
    private final ScaleDtoMapper scaleDtoMapper;

    public ScaleController(ScaleUseCase scaleUseCase, ScaleDtoMapper scaleDtoMapper) {
        this.scaleUseCase = scaleUseCase;
        this.scaleDtoMapper = scaleDtoMapper;
    }

    @Override
    public ResponseEntity<List<ScaleDto>> getScales(ScaleDto filter) {
        Scale scaleFilter = scaleDtoMapper.toEntity(filter);
        List<Scale> scales = scaleUseCase.getScalesByFilter(scaleFilter);
        List<ScaleDto> responseDtos = scales.stream().map(scaleDtoMapper::toDto).toList();
        return ResponseEntity.ok(responseDtos);
    }

    @Override
    public ResponseEntity<ScaleDto> createUpdateScale(ScaleDto dto) {
        Scale scale = scaleDtoMapper.toEntity(dto);
        Scale saved = scaleUseCase.createUpdateScale(scale);
        ScaleDto responseDto = scaleDtoMapper.toDto(saved);
        if (dto.getId() == null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } else {
            return ResponseEntity.ok(responseDto);
        }
    }

    @Override
    public ResponseEntity<Void> deleteScale(UUID scaleId) {
        scaleUseCase.deleteScale(scaleId);
        return ResponseEntity.noContent().build();
    }
}
