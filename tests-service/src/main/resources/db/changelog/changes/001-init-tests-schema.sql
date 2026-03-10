--liquibase formatted sql
--changeset ivantolkach:001-init-tests-schema
CREATE TABLE tests.Test (
                            id UUID PRIMARY KEY,
                            name VARCHAR(100) NOT NULL,
                            methodology_id UUID NOT NULL,
                            is_active BOOLEAN DEFAULT FALSE,
                            created_by UUID,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            updated_by UUID,
                            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            CONSTRAINT fk_test_methodology FOREIGN KEY (methodology_id) REFERENCES methodologies.Methodology(id) ON DELETE RESTRICT ON UPDATE CASCADE,
                            CONSTRAINT fk_test_created_by FOREIGN KEY (created_by) REFERENCES users.Admin(id) ON DELETE RESTRICT ON UPDATE CASCADE,
                            CONSTRAINT fk_test_updated_by FOREIGN KEY (updated_by) REFERENCES users.Admin(id) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE tests.Question (
                                id UUID PRIMARY KEY,
                                test_id UUID NOT NULL,
                                text TEXT NOT NULL,
                                type INTEGER DEFAULT 0,
                                position INTEGER NOT NULL,
                                CONSTRAINT fk_question_test FOREIGN KEY (test_id) REFERENCES tests.Test(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE tests.AnswerOption (
                                    id UUID PRIMARY KEY,
                                    question_id UUID NOT NULL,
                                    text TEXT NOT NULL,
                                    score INTEGER DEFAULT 0,
                                    CONSTRAINT fk_answeroption_question FOREIGN KEY (question_id) REFERENCES tests.Question(id) ON DELETE CASCADE ON UPDATE CASCADE
);

ALTER TABLE methodologies.ScaleQuestion
    ADD CONSTRAINT fk_scalequestion_question
        FOREIGN KEY (question_id) REFERENCES tests.Question(id)
            ON DELETE RESTRICT ON UPDATE CASCADE;

--rollback ALTER TABLE methodologies.ScaleQuestion DROP CONSTRAINT fk_scalequestion_question;
--rollback DROP TABLE tests.AnswerOption;
--rollback DROP TABLE tests.Question;
--rollback DROP TABLE tests.Test;
--rollback DROP SCHEMA IF EXISTS tests CASCADE;