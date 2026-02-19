package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest;

import dev.tolkach.psychologicalTestsApplication.domain.model.StudentAnswer;
import dev.tolkach.psychologicalTestsApplication.domain.port.in.StudentAnswerUseCase;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.dto.StudentAnswerDto;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.endpoint.StudentAnswerEndpoint;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.mapper.StudentAnswerDtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class StudentAnswerController implements StudentAnswerEndpoint {

    private final StudentAnswerUseCase studentAnswerUseCase;
    private final StudentAnswerDtoMapper studentAnswerDtoMapper;

    public StudentAnswerController(StudentAnswerUseCase studentAnswerUseCase, StudentAnswerDtoMapper studentAnswerDtoMapper) {
        this.studentAnswerUseCase = studentAnswerUseCase;
        this.studentAnswerDtoMapper = studentAnswerDtoMapper;
    }

    @Override
    public ResponseEntity<List<StudentAnswerDto>> getStudentAnswers(StudentAnswerDto filter) {
        StudentAnswer studentAnswerFilter = studentAnswerDtoMapper.toEntity(filter);
        List<StudentAnswer> studentAnswers = studentAnswerUseCase.getStudentAnswersByFilter(studentAnswerFilter);
        List<StudentAnswerDto> responseDtos = studentAnswers.stream().map(studentAnswerDtoMapper::toDto).toList();
        return ResponseEntity.ok(responseDtos);
    }

    @Override
    public ResponseEntity<StudentAnswerDto> createUpdateStudentAnswer(StudentAnswerDto dto) {
        StudentAnswer studentAnswer = studentAnswerDtoMapper.toEntity(dto);
        StudentAnswer saved = studentAnswerUseCase.createUpdateStudentAnswer(studentAnswer);
        StudentAnswerDto responseDto = studentAnswerDtoMapper.toDto(saved);
        if (dto.getId() == null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } else {
            return ResponseEntity.ok(responseDto);
        }
    }

    @Override
    public ResponseEntity<Void> deleteStudentAnswer(UUID studentAnswerId) {
        studentAnswerUseCase.deleteStudentAnswer(studentAnswerId);
        return ResponseEntity.noContent().build();
    }
}