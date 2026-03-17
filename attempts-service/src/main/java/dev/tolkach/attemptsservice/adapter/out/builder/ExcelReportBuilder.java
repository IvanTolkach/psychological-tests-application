package dev.tolkach.attemptsservice.adapter.out.builder;

import common.dto.AnswerOptionDto;
import dev.tolkach.attemptsservice.application.model.StudentAnswer;
import dev.tolkach.attemptsservice.application.model.TestAttempt;
import dev.tolkach.attemptsservice.application.port.out.ExcelReportBuilderPort;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ExcelReportBuilder implements ExcelReportBuilderPort {

    record OptionFaculty(UUID optionId, UUID facultyId) {}

    @Override
    public byte[] buildReport(
            int totalStudents,
            List<UUID> faculties,
            Map<UUID,String> facultyNames,
            LinkedHashMap<UUID,String> questionTexts,
            Map<UUID, UUID> studentFaculty,
            List<TestAttempt> attempts,
            Map<UUID, List<StudentAnswer>> answersByQuestion,
            Map<UUID, List<AnswerOptionDto>> optionsByQuestion) {

        try (Workbook wb = new XSSFWorkbook()) {

            Sheet sheet = wb.createSheet("Report");

            DataFormat format = wb.createDataFormat();

            CellStyle border = wb.createCellStyle();
            border.setBorderBottom(BorderStyle.THIN);
            border.setBorderTop(BorderStyle.THIN);
            border.setBorderLeft(BorderStyle.THIN);
            border.setBorderRight(BorderStyle.THIN);

            Font boldFont = wb.createFont();
            boldFont.setBold(true);

            CellStyle bold = wb.createCellStyle();
            bold.cloneStyleFrom(border);
            bold.setFont(boldFont);

            CellStyle center = wb.createCellStyle();
            center.cloneStyleFrom(border);
            center.setAlignment(HorizontalAlignment.CENTER);
            center.setVerticalAlignment(VerticalAlignment.CENTER);

            CellStyle centerBold = wb.createCellStyle();
            centerBold.cloneStyleFrom(center);
            centerBold.setFont(boldFont);

            CellStyle gray = wb.createCellStyle();
            gray.cloneStyleFrom(border);
            gray.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            gray.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            CellStyle grayBold = wb.createCellStyle();
            grayBold.cloneStyleFrom(gray);
            grayBold.setFont(boldFont);

            CellStyle percent = wb.createCellStyle();
            percent.cloneStyleFrom(border);
            percent.setDataFormat(wb.createDataFormat().getFormat("0.0"));

            CellStyle grayPercent = wb.createCellStyle();
            grayPercent.cloneStyleFrom(grayBold);
            grayPercent.setDataFormat(wb.createDataFormat().getFormat("0.0"));

            CellStyle wrapBold = wb.createCellStyle();
            wrapBold.cloneStyleFrom(bold);
            wrapBold.setWrapText(true);

            CellStyle wrap = wb.createCellStyle();
            wrap.cloneStyleFrom(border);
            wrap.setWrapText(true);

            CellStyle headerCenterBold = wb.createCellStyle();
            headerCenterBold.cloneStyleFrom(bold);
            headerCenterBold.setAlignment(HorizontalAlignment.CENTER);
            headerCenterBold.setVerticalAlignment(VerticalAlignment.CENTER);
            headerCenterBold.setWrapText(true);

            Map<UUID, UUID> attemptStudent =
                    attempts.stream().collect(Collectors.toMap(
                            TestAttempt::getId,
                            TestAttempt::getStudentId
                    ));

            Map<UUID, Long> facultyStudentCount =
                    attempts.stream().collect(Collectors.groupingBy(
                            a -> studentFaculty.get(a.getStudentId()),
                            Collectors.counting()
                    ));

            Map<OptionFaculty, Long> optionFacultyCount = new HashMap<>();
            Map<UUID, Long> optionTotalCount = new HashMap<>();

            for (List<StudentAnswer> answers : answersByQuestion.values()) {

                for (StudentAnswer answer : answers) {

                    UUID optionId = answer.getAnswerOptionId();
                    UUID attemptId = answer.getTestAttemptId();

                    UUID studentId = attemptStudent.get(attemptId);
                    if (studentId == null) continue;

                    UUID facultyId = studentFaculty.get(studentId);
                    if (facultyId == null) continue;

                    OptionFaculty key = new OptionFaculty(optionId, facultyId);

                    optionFacultyCount.merge(key, 1L, Long::sum);
                    optionTotalCount.merge(optionId, 1L, Long::sum);
                }
            }

            int rowIdx = 0;

            Row title = sheet.createRow(rowIdx++);
            Cell titleCell = title.createCell(0);

            String text = "Количество опрошенных обучающихся: "
                    + totalStudents
                    + " человек.";

            XSSFRichTextString rich = new XSSFRichTextString(text);

            int start = text.indexOf(String.valueOf(totalStudents));
            int end = start + String.valueOf(totalStudents).length();

            rich.applyFont(start, end, boldFont);

            titleCell.setCellValue(rich);

            rowIdx++;

            int questionNumber = 1;

            for (UUID questionId : questionTexts.keySet()) {

                Row qRow = sheet.createRow(rowIdx);

                Cell numCell = qRow.createCell(0);
                numCell.setCellValue(questionNumber + ".");
                numCell.setCellStyle(centerBold);

                Cell textCell = qRow.createCell(1);
                textCell.setCellValue(questionTexts.getOrDefault(questionId,""));
                textCell.setCellStyle(wrapBold);

                int col = 2;

                for (UUID faculty : faculties) {

                    Cell c = qRow.createCell(col++);
                    c.setCellValue(facultyNames.getOrDefault(faculty,""));
                    c.setCellStyle(headerCenterBold);
                }

                Cell uni = qRow.createCell(col);
                uni.setCellValue("БНТУ");
                uni.setCellStyle(grayBold);

                rowIdx++;

                List<AnswerOptionDto> options =
                        optionsByQuestion.getOrDefault(questionId, List.of());

                int optionNumber = 1;

                for (AnswerOptionDto option : options) {

                    Row row = sheet.createRow(rowIdx++);

                    Cell num = row.createCell(0);
                    num.setCellValue(optionNumber++);
                    num.setCellStyle(center);

                    Cell textCellOpt = row.createCell(1);
                    textCellOpt.setCellValue(option.getText());
                    textCellOpt.setCellStyle(wrap);

                    int c = 2;

                    for (UUID faculty : faculties) {

                        long facultyStudents =
                                facultyStudentCount.getOrDefault(faculty,0L);

                        long selected =
                                optionFacultyCount.getOrDefault(
                                        new OptionFaculty(option.getId(), faculty),
                                        0L
                                );

                        double percentValue =
                                facultyStudents == 0
                                        ? 0
                                        : (double) selected * 100 / facultyStudents;

                        Cell cell = row.createCell(c++);
                        cell.setCellValue(percentValue);
                        cell.setCellStyle(percent);
                    }

                    long totalSelected =
                            optionTotalCount.getOrDefault(option.getId(),0L);

                    double uniPercent =
                            totalStudents == 0
                                    ? 0
                                    : (double) totalSelected * 100 / totalStudents;

                    Cell uniCell = row.createCell(c);
                    uniCell.setCellValue(uniPercent);
                    uniCell.setCellStyle(grayPercent);

                    qRow.setHeight((short) -1);
                    row.setHeight((short) -1);
                }

                rowIdx++;
                questionNumber++;
            }

            sheet.setColumnWidth(0,1500);

            int totalCols = faculties.size() + 3;

            for (int i=1;i<totalCols;i++){
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            wb.write(out);

            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
