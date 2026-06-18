--liquibase formatted sql
--changeset ivantolkach:002-creating-indexes
--comment Создание индексов для часто используемых условий фильтрации и соединений (ускорение аналитики и отчётов)

CREATE INDEX idx_studentanswer_attempt
    ON attempts.studentanswer(test_attempt_id);

CREATE INDEX idx_scalequestion_question
    ON methodologies.scalequestion(question_id);

CREATE INDEX idx_scorerange_scale
    ON methodologies.scorerange(scale_id);

COMMENT ON INDEX attempts.idx_studentanswer_attempt IS 'Ускоряет выбор всех ответов студента по конкретной попытке прохождения теста';
COMMENT ON INDEX methodologies.idx_scalequestion_question IS 'Ускоряет поиск всех шкал, в которые входит конкретный вопрос (при создании/редактировании теста и валидации)';
COMMENT ON INDEX methodologies.idx_scorerange_scale IS 'Ускоряет поиск нужного диапазона интерпретации для конкретной шкалы при обработке результатов';

--rollback  DROP INDEX IF EXISTS attempts.idx_studentanswer_attempt;
--rollback  DROP INDEX IF EXISTS methodologies.idx_scalequestion_question;
--rollback  DROP INDEX IF EXISTS methodologies.idx_scorerange_scale;