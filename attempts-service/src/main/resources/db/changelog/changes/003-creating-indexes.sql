--liquibase formatted sql
--changeset ivantolkach:003-creating-indexes
CREATE INDEX idx_studentanswer_attempt
    ON attempts.studentanswer(test_attempt_id);

CREATE INDEX idx_scalequestion_question
    ON methodologies.scalequestion(question_id);

CREATE INDEX idx_scorerange_scale
    ON methodologies.scorerange(scale_id);

--rollback  DROP INDEX IF EXISTS attempts.idx_studentanswer_attempt;
--rollback  DROP INDEX IF EXISTS methodologies.idx_scalequestion_question;
--rollback  DROP INDEX IF EXISTS methodologies.idx_scorerange_scale;