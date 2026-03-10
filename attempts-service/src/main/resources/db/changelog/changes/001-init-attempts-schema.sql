--liquibase formatted sql
--changeset ivantolkach:001-init-attempts-schema
CREATE TABLE attempts.TestAttempt (
                                      id UUID PRIMARY KEY,
                                      student_id UUID NOT NULL,
                                      test_id UUID NOT NULL,
                                      attempt_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      CONSTRAINT fk_attempt_student FOREIGN KEY (student_id) REFERENCES users.Student(id) ON DELETE RESTRICT ON UPDATE CASCADE,
                                      CONSTRAINT fk_attempt_test FOREIGN KEY (test_id) REFERENCES tests.Test(id) ON DELETE RESTRICT ON UPDATE CASCADE
);


CREATE TABLE attempts.TestAttemptScore (
                                           id UUID PRIMARY KEY,
                                           test_attempt_id UUID NOT NULL,
                                           scale_id UUID NOT NULL,
                                           score INTEGER NOT NULL DEFAULT 0,
                                           interpretation VARCHAR(255) NOT NULL DEFAULT 'undefined',
                                           CONSTRAINT fk_score_attempt FOREIGN KEY (test_attempt_id) REFERENCES attempts.TestAttempt(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                           CONSTRAINT fk_score_scale FOREIGN KEY (scale_id) REFERENCES methodologies.Scale(id) ON DELETE RESTRICT ON UPDATE CASCADE
);


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

--rollback DROP TABLE attempts.StudentAnswer;
--rollback DROP TABLE attempts.TestAttemptScore;
--rollback DROP TABLE attempts.TestAttempt;
--rollback DROP SCHEMA IF EXISTS attempts CASCADE;