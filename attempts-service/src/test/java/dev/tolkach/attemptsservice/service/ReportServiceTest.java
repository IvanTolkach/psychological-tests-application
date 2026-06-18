package dev.tolkach.attemptsservice.service;

import common.dto.AnswerOptionDto;
import common.dto.FacultyDto;
import common.dto.QuestionDto;
import common.dto.StudentDto;
import dev.tolkach.attemptsservice.application.model.ReportRequest;
import dev.tolkach.attemptsservice.application.model.StudentAnswer;
import dev.tolkach.attemptsservice.application.model.TestAttempt;
import dev.tolkach.attemptsservice.application.port.out.*;
import dev.tolkach.attemptsservice.application.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    TestAttemptRepository attemptRepository;

    @Mock
    StudentAnswerRepository answerRepository;

    @Mock
    TestsPort testsPort;

    @Mock
    UsersPort usersPort;

    @Mock
    ExcelReportBuilderPort excelReportBuilder;

    @InjectMocks
    ReportService service;

    UUID testId;
    UUID facultyId;
    UUID studentId;
    UUID attemptId;
    UUID questionId;

    @BeforeEach
    void setup() {
        testId = UUID.randomUUID();
        facultyId = UUID.randomUUID();
        studentId = UUID.randomUUID();
        attemptId = UUID.randomUUID();
        questionId = UUID.randomUUID();

        service = new ReportService(
                attemptRepository,
                answerRepository,
                testsPort,
                usersPort,
                excelReportBuilder
        );
    }


    @Test
    void generateReport_testIdNull() {

        ReportRequest req = new ReportRequest();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.generateReport(req)
        );
    }

    @Test
    void generateReport_multipleAttempts_sameStudent_keepsLatest() {
        ReportRequest req = new ReportRequest();
        req.setTestId(testId);

        FacultyDto f = new FacultyDto();
        f.setId(facultyId);
        f.setName("Faculty A");

        StudentDto s = new StudentDto();
        s.setId(studentId);
        s.setFacultyId(facultyId);

        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC+3"));

        TestAttempt oldAttempt = new TestAttempt();
        oldAttempt.setId(UUID.randomUUID());
        oldAttempt.setStudentId(studentId);
        oldAttempt.setAttemptDate(now.minusDays(5));

        TestAttempt latestAttempt = new TestAttempt();
        latestAttempt.setId(attemptId);
        latestAttempt.setStudentId(studentId);
        latestAttempt.setAttemptDate(now);

        QuestionDto q = new QuestionDto();
        q.setId(questionId);
        q.setText("Question 1");
        q.setPosition(1);

        when(usersPort.getAllFaculties()).thenReturn(List.of(f));
        when(usersPort.searchStudents(argThat(filter -> facultyId.equals(filter.getFacultyId()))))
                .thenReturn(List.of(s));
        when(attemptRepository.findByFilter(any())).thenReturn(List.of(oldAttempt, latestAttempt));
        when(testsPort.getQuestionsByTestId(testId)).thenReturn(List.of(q));
        when(testsPort.getAnswerOptionsByQuestionIds(any())).thenReturn(List.of());
        when(answerRepository.findByFilter(any())).thenReturn(List.of());

        when(excelReportBuilder.buildReport(
                anyInt(), any(), any(), any(), any(), any(), any(), any()
        )).thenReturn(new byte[]{1});

        byte[] report = service.generateReport(req);

        assertNotNull(report);

        verify(excelReportBuilder).buildReport(
                eq(1), any(), any(), any(), any(),
                argThat(list -> list.size() == 1 && list.getFirst().getId().equals(attemptId)),
                any(), any()
        );
    }

    @Test
    void generateReport_questionsWithDuplicateIds_mergeFunctionUsed() {
        ReportRequest req = new ReportRequest();
        req.setTestId(testId);

        FacultyDto f = new FacultyDto();
        f.setId(facultyId);
        f.setName("F");

        StudentDto s = new StudentDto();
        s.setId(studentId);
        s.setFacultyId(facultyId);

        TestAttempt attempt = new TestAttempt();
        attempt.setId(attemptId);
        attempt.setStudentId(studentId);
        attempt.setAttemptDate(LocalDateTime.now(ZoneId.of("UTC+3")));

        QuestionDto q1 = new QuestionDto();
        q1.setId(questionId);
        q1.setText("Question A");
        q1.setPosition(1);
        QuestionDto q2 = new QuestionDto();
        q2.setId(questionId);
        q2.setText("Question B");
        q2.setPosition(2);

        when(usersPort.getAllFaculties()).thenReturn(List.of(f));
        when(usersPort.searchStudents(any())).thenReturn(List.of(s));
        when(attemptRepository.findByFilter(any())).thenReturn(List.of(attempt));
        when(testsPort.getQuestionsByTestId(testId)).thenReturn(List.of(q1, q2));
        when(testsPort.getAnswerOptionsByQuestionIds(any())).thenReturn(List.of());
        when(answerRepository.findByFilter(any())).thenReturn(List.of());

        when(excelReportBuilder.buildReport(
                anyInt(), any(), any(), any(), any(), any(), any(), any()
        )).thenReturn(new byte[]{1});

        byte[] result = service.generateReport(req);

        assertNotNull(result);

        verify(excelReportBuilder).buildReport(
                eq(1),
                any(),
                any(),
                argThat(map -> map.containsKey(questionId) && map.size() == 1),
                any(),
                any(),
                any(),
                any()
        );
    }

    @Test
    void generateReport_noStudents() {
        ReportRequest req = new ReportRequest();
        req.setTestId(testId);

        FacultyDto f = new FacultyDto();
        f.setId(facultyId);
        f.setName("F");

        when(usersPort.getAllFaculties()).thenReturn(List.of(f));
        when(usersPort.searchStudents(any())).thenReturn(List.of());

        assertThrows(
                IllegalStateException.class,
                () -> service.generateReport(req)
        );
    }

    @Test
    void generateReport_success() {
        ReportRequest req = new ReportRequest();
        req.setTestId(testId);

        FacultyDto f = new FacultyDto();
        f.setId(facultyId);
        f.setName("F");

        StudentDto s = new StudentDto();
        s.setId(studentId);
        s.setFacultyId(facultyId);

        TestAttempt a = new TestAttempt();
        a.setId(attemptId);
        a.setStudentId(studentId);
        a.setAttemptDate(LocalDateTime.now(ZoneId.of("UTC+3")));

        QuestionDto q = new QuestionDto();
        q.setId(questionId);
        q.setPosition(1);
        q.setText("Q");

        AnswerOptionDto opt = new AnswerOptionDto();
        opt.setQuestionId(questionId);

        StudentAnswer ans = new StudentAnswer();
        ans.setQuestionId(questionId);

        when(usersPort.getAllFaculties()).thenReturn(List.of(f));
        when(usersPort.searchStudents(any())).thenReturn(List.of(s));
        when(attemptRepository.findByFilter(any())).thenReturn(List.of(a));
        when(testsPort.getQuestionsByTestId(testId)).thenReturn(List.of(q));
        when(testsPort.getAnswerOptionsByQuestionIds(any())).thenReturn(List.of(opt));
        when(answerRepository.findByFilter(any())).thenReturn(List.of(ans));

        when(excelReportBuilder.buildReport(anyInt(), any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(new byte[]{1, 2, 3});

        byte[] result = service.generateReport(req);

        assertNotNull(result);

        verify(excelReportBuilder).buildReport(
                anyInt(), any(), any(), any(), any(), any(), any(), any()
        );
    }
}
