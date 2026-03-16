--liquibase formatted sql
--changeset ivantolkach:001-init-attempts-schema
--comment Сущности попыток прохождения, результатов по шкалам и протокола ответов микросервиса attempts-service

CREATE TABLE attempts.TestAttempt (
                                      id UUID PRIMARY KEY,
                                      student_id UUID NOT NULL,
                                      test_id UUID NOT NULL,
                                      attempt_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      CONSTRAINT fk_attempt_student FOREIGN KEY (student_id) REFERENCES users.Student(id) ON DELETE RESTRICT ON UPDATE CASCADE,
                                      CONSTRAINT fk_attempt_test FOREIGN KEY (test_id) REFERENCES tests.Test(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

COMMENT ON TABLE attempts.TestAttempt IS 'Факт попытки прохождения теста конкретным студентом';
COMMENT ON COLUMN attempts.TestAttempt.id IS 'Уникальный идентификатор попытки';
COMMENT ON COLUMN attempts.TestAttempt.student_id IS 'Студент, проходивший тест';
COMMENT ON COLUMN attempts.TestAttempt.test_id IS 'Какой тест проходился';
COMMENT ON COLUMN attempts.TestAttempt.attempt_date IS 'Дата и время начала/завершения попытки';

CREATE TABLE attempts.TestAttemptScore (
                                           id UUID PRIMARY KEY,
                                           test_attempt_id UUID NOT NULL,
                                           scale_id UUID NOT NULL,
                                           score INTEGER NOT NULL DEFAULT 0,
                                           interpretation VARCHAR(255) NOT NULL DEFAULT 'undefined',
                                           CONSTRAINT fk_score_attempt FOREIGN KEY (test_attempt_id) REFERENCES attempts.TestAttempt(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                           CONSTRAINT fk_score_scale FOREIGN KEY (scale_id) REFERENCES methodologies.Scale(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

COMMENT ON TABLE attempts.TestAttemptScore IS 'Рассчитанные баллы и интерпретации по каждой шкале для попытки';
COMMENT ON COLUMN attempts.TestAttemptScore.id IS 'Уникальный идентификатор результата по шкале';
COMMENT ON COLUMN attempts.TestAttemptScore.test_attempt_id IS 'Попытка прохождения';
COMMENT ON COLUMN attempts.TestAttemptScore.scale_id IS 'Шкала методики';
COMMENT ON COLUMN attempts.TestAttemptScore.score IS 'Набранный сырой балл по шкале';
COMMENT ON COLUMN attempts.TestAttemptScore.interpretation IS 'Текстовый уровень (низкий, умеренный, тяжёлый и т.д.) — заполняется автоматически';

CREATE TABLE attempts.StudentAnswer (
                                        id UUID PRIMARY KEY,
                                        test_attempt_id UUID NOT NULL,
                                        question_id UUID NOT NULL,
                                        answer_option_id UUID,
                                        answer_value TEXT,
                                        CONSTRAINT fk_answer_attempt FOREIGN KEY (test_attempt_id) REFERENCES attempts.TestAttempt(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                        CONSTRAINT fk_answer_question FOREIGN KEY (question_id) REFERENCES tests.Question(id) ON DELETE RESTRICT ON UPDATE CASCADE,
                                        CONSTRAINT fk_answer_option FOREIGN KEY (answer_option_id) REFERENCES tests.AnswerOption(id) ON DELETE SET NULL ON UPDATE CASCADE
);

COMMENT ON TABLE attempts.StudentAnswer IS 'Полный протокол ответов студента на каждый вопрос в попытке';
COMMENT ON COLUMN attempts.StudentAnswer.id IS 'Уникальный идентификатор ответа';
COMMENT ON COLUMN attempts.StudentAnswer.test_attempt_id IS 'Попытка прохождения';
COMMENT ON COLUMN attempts.StudentAnswer.question_id IS 'Вопрос, на который дан ответ';
COMMENT ON COLUMN attempts.StudentAnswer.answer_option_id IS 'Выбранный вариант ответа (для вопросов с выбором)';
COMMENT ON COLUMN attempts.StudentAnswer.answer_value IS 'Текстовый ответ (для открытых вопросов)';

--rollback DROP TABLE attempts.StudentAnswer CASCADE;
--rollback DROP TABLE attempts.TestAttemptScore CASCADE;
--rollback DROP TABLE attempts.TestAttempt CASCADE;
--rollback DROP SCHEMA IF EXISTS attempts CASCADE;