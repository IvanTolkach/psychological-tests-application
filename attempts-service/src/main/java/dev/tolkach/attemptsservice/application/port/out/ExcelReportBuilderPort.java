package dev.tolkach.attemptsservice.application.port.out;

import common.dto.AnswerOptionDto;
import dev.tolkach.attemptsservice.application.model.StudentAnswer;
import dev.tolkach.attemptsservice.application.model.TestAttempt;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ExcelReportBuilderPort {
    byte[] buildReport(
            int totalStudents,
            List<UUID> faculties,
            Map<UUID,String> facultyNames,
            LinkedHashMap<UUID,String> questionTexts,
            Map<UUID, UUID> studentFaculty,
            List<TestAttempt> attempts,
            Map<UUID, List<StudentAnswer>> answersByQuestion,
            Map<UUID, List<AnswerOptionDto>> optionsByQuestion
    );
}
