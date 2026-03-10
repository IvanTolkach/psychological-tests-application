package dev.tolkach.attemptsservice.application.service;

import common.dto.AnswerOptionDto;
import common.dto.FacultyDto;
import common.dto.QuestionDto;
import common.dto.StudentDto;
import dev.tolkach.attemptsservice.adapter.out.builder.ExcelReportBuilder;
import dev.tolkach.attemptsservice.application.model.ReportRequest;
import dev.tolkach.attemptsservice.application.model.StudentAnswer;
import dev.tolkach.attemptsservice.application.model.TestAttempt;
import dev.tolkach.attemptsservice.application.model.TestAttemptFilter;
import dev.tolkach.attemptsservice.application.port.in.ReportUseCase;
import dev.tolkach.attemptsservice.application.port.out.StudentAnswerRepository;
import dev.tolkach.attemptsservice.application.port.out.TestAttemptRepository;
import dev.tolkach.attemptsservice.application.port.out.TestsPort;
import dev.tolkach.attemptsservice.application.port.out.UsersPort;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

public class ReportService implements ReportUseCase {

    private final TestAttemptRepository testAttemptRepository;
    private final StudentAnswerRepository studentAnswerRepository;
    private final TestsPort testsPort;
    private final UsersPort studentsPort;

    public ReportService(TestAttemptRepository testAttemptRepository, StudentAnswerRepository studentAnswerRepository, TestsPort testsPort, UsersPort studentsPort) {
        this.testAttemptRepository = testAttemptRepository;
        this.studentAnswerRepository = studentAnswerRepository;
        this.testsPort = testsPort;
        this.studentsPort = studentsPort;
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] generateReport(ReportRequest request) {

        if (request.getTestId() == null) {
            throw new IllegalArgumentException("testId is required");
        }

        List<FacultyDto> facultiesDto = studentsPort.getAllFaculties();

        Map<UUID, String> facultyNames = facultiesDto.stream()
                .collect(Collectors.toMap(
                        FacultyDto::getId,
                        FacultyDto::getName
                ));

        List<UUID> facultyIds = request.getFacultyIds();

        if (facultyIds == null || facultyIds.isEmpty()) {
            facultyIds = facultiesDto.stream()
                    .map(FacultyDto::getId)
                    .collect(Collectors.toList());
        }

        Map<UUID, UUID> studentFacultyMap = new HashMap<>();

        for (UUID facultyId : facultyIds) {

            StudentDto filter = new StudentDto();
            filter.setFacultyId(facultyId);

            List<StudentDto> students = studentsPort.searchStudents(filter);

            for (StudentDto s : students) {
                studentFacultyMap.put(s.getId(), facultyId);
            }
        }

        if (studentFacultyMap.isEmpty()) {
            throw new IllegalStateException("No students found");
        }

        TestAttemptFilter filter = new TestAttemptFilter();
        filter.setTestId(request.getTestId());
        filter.setAttemptDateFrom(request.getDateFrom());
        filter.setAttemptDateTo(request.getDateTo());

        List<TestAttempt> attempts =
                testAttemptRepository.findByFilter(filter);

        List<TestAttempt> filteredAttempts =
                attempts.stream()
                        .filter(a -> studentFacultyMap.containsKey(a.getStudentId()))
                        .toList();

        Map<UUID, TestAttempt> latestAttempts = new HashMap<>();

        for (TestAttempt a : filteredAttempts) {

            TestAttempt existing = latestAttempts.get(a.getStudentId());

            if (existing == null ||
                    a.getAttemptDate().isAfter(existing.getAttemptDate())) {

                latestAttempts.put(a.getStudentId(), a);
            }
        }

        List<TestAttempt> uniqueAttempts =
                new ArrayList<>(latestAttempts.values());

        int totalStudents = uniqueAttempts.size();

        List<QuestionDto> questions =
                testsPort.getQuestionsByTestId(request.getTestId());

        List<QuestionDto> sortedQuestions =
                questions.stream()
                        .sorted(Comparator.comparing(QuestionDto::getPosition))
                        .toList();

        LinkedHashMap<UUID,String> questionTexts =
                sortedQuestions.stream()
                        .collect(Collectors.toMap(
                                QuestionDto::getId,
                                QuestionDto::getText,
                                (a,b)->a,
                                LinkedHashMap::new
                        ));

        List<UUID> questionIds =
                questions.stream()
                        .map(QuestionDto::getId)
                        .toList();

        List<AnswerOptionDto> options =
                testsPort.getAnswerOptionsByQuestionIds(questionIds);

        Map<UUID, List<AnswerOptionDto>> optionsByQuestion =
                options.stream()
                        .collect(Collectors.groupingBy(
                                AnswerOptionDto::getQuestionId
                        ));

        Map<UUID, List<StudentAnswer>> answersByQuestion = new HashMap<>();

        for (TestAttempt attempt : uniqueAttempts) {

            StudentAnswer filterAnswer = new StudentAnswer();
            filterAnswer.setTestAttemptId(attempt.getId());

            List<StudentAnswer> answers =
                    studentAnswerRepository.findByFilter(filterAnswer);

            for (StudentAnswer answer : answers) {

                answersByQuestion
                        .computeIfAbsent(
                                answer.getQuestionId(),
                                k -> new ArrayList<>()
                        )
                        .add(answer);
            }
        }

        return ExcelReportBuilder.build(
                totalStudents,
                facultyIds,
                facultyNames,
                questionTexts,
                studentFacultyMap,
                uniqueAttempts,
                answersByQuestion,
                optionsByQuestion
        );
    }
}
