package dev.tolkach.attemptsservice.adapter.out;

import common.dto.AnswerOptionDto;
import dev.tolkach.attemptsservice.adapter.out.builder.ExcelReportBuilder;
import dev.tolkach.attemptsservice.application.model.StudentAnswer;
import dev.tolkach.attemptsservice.application.model.TestAttempt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExcelReportBuilderTest {

    ExcelReportBuilder builder;

    @BeforeEach
    void setUp() {
        builder = new ExcelReportBuilder();
    }

    @Test
    void buildReport_fullCoverage() {

        UUID faculty1 = UUID.randomUUID();
        UUID faculty2 = UUID.randomUUID();

        UUID questionId = UUID.randomUUID();
        UUID optionId = UUID.randomUUID();

        UUID studentId = UUID.randomUUID();
        UUID attemptId = UUID.randomUUID();

        TestAttempt attempt = mock(TestAttempt.class);
        when(attempt.getId()).thenReturn(attemptId);
        when(attempt.getStudentId()).thenReturn(studentId);

        StudentAnswer answer = mock(StudentAnswer.class);
        when(answer.getAnswerOptionId()).thenReturn(optionId);
        when(answer.getTestAttemptId()).thenReturn(attemptId);

        AnswerOptionDto optionDto = new AnswerOptionDto();
        optionDto.setId(optionId);
        optionDto.setText("Option text");

        byte[] result = builder.buildReport(
                10,
                List.of(faculty1, faculty2),
                Map.of(faculty1, "F1", faculty2, "F2"),
                new LinkedHashMap<>(Map.of(questionId, "Question?")),
                Map.of(studentId, faculty1),
                List.of(attempt),
                Map.of(questionId, List.of(answer)),
                Map.of(questionId, List.of(optionDto))
        );

        assertNotNull(result);
        assertTrue(result.length > 0);
    }

    @Test
    void buildReport_shouldSkipWhenStudentOrFacultyNull() {

        UUID questionId = UUID.randomUUID();
        UUID optionId = UUID.randomUUID();

        UUID studentId = UUID.randomUUID();
        UUID attemptId = UUID.randomUUID();
        UUID anotherAttemptId = UUID.randomUUID();

        UUID faculty = UUID.randomUUID();

        TestAttempt attempt = mock(TestAttempt.class);
        when(attempt.getId()).thenReturn(attemptId);
        when(attempt.getStudentId()).thenReturn(studentId);

        StudentAnswer answer = mock(StudentAnswer.class);
        when(answer.getAnswerOptionId()).thenReturn(optionId);
        when(answer.getTestAttemptId()).thenReturn(anotherAttemptId);

        byte[] result = builder.buildReport(
                5,
                List.of(faculty),
                Map.of(faculty, "F"),
                new LinkedHashMap<>(Map.of(questionId, "Q")),
                Map.of(studentId, faculty),
                List.of(attempt),
                Map.of(questionId, List.of(answer)),
                Map.of(questionId, List.of())
        );

        assertNotNull(result);
    }

    @Test
    void buildReport_zeroStudents_shouldHandleDivisionByZero() {

        UUID faculty = UUID.randomUUID();
        UUID questionId = UUID.randomUUID();
        UUID optionId = UUID.randomUUID();

        AnswerOptionDto option = new AnswerOptionDto();
        option.setId(optionId);
        option.setText("opt");

        byte[] result = builder.buildReport(
                0,
                List.of(faculty),
                Map.of(faculty, "F"),
                new LinkedHashMap<>(Map.of(questionId, "Q")),
                Map.of(),
                List.of(),
                Map.of(questionId, List.of()),
                Map.of(questionId, List.of(option))
        );

        assertNotNull(result);
    }

    @Test
    void buildReport_emptyData() {

        byte[] result = builder.buildReport(
                0,
                List.of(),
                Map.of(),
                new LinkedHashMap<>(),
                Map.of(),
                List.of(),
                Map.of(),
                Map.of()
        );

        assertNotNull(result);
    }

    @Test
    void buildReport_shouldThrowRuntimeException_onError() {

        assertThrows(RuntimeException.class, () ->
                builder.buildReport(
                        1,
                        null,
                        Map.of(),
                        new LinkedHashMap<>(),
                        Map.of(),
                        List.of(),
                        Map.of(),
                        Map.of()
                )
        );
    }
}
