--liquibase formatted sql
--changeset ivantolkach:003-init-methodologies-schema

CREATE SCHEMA IF NOT EXISTS methodologies;

CREATE TABLE methodologies.Methodology (
                                           id UUID PRIMARY KEY,
                                           name VARCHAR(100) NOT NULL UNIQUE,
                                           description TEXT
);

CREATE TABLE methodologies.Scale (
                                     id UUID PRIMARY KEY,
                                     methodology_id UUID NOT NULL,
                                     name VARCHAR(100) NOT NULL,
                                     is_total BOOLEAN DEFAULT FALSE,
                                     description TEXT,
                                     CONSTRAINT fk_scale_methodology FOREIGN KEY (methodology_id) REFERENCES methodologies.Methodology(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE methodologies.ScoreRange (
                                          id UUID PRIMARY KEY,
                                          scale_id UUID NOT NULL,
                                          min_score INTEGER NOT NULL,
                                          max_score INTEGER NOT NULL,
                                          interpretation VARCHAR(255) NOT NULL,
                                          description TEXT,
                                          CONSTRAINT fk_scorerange_scale FOREIGN KEY (scale_id) REFERENCES methodologies.Scale(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE methodologies.ScaleQuestion (
                                     id UUID PRIMARY KEY,
                                     scale_id UUID NOT NULL,
                                     question_id UUID NOT NULL,
                                     CONSTRAINT fk_scalequestion_scale FOREIGN KEY (scale_id) REFERENCES methodologies.Scale(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                     CONSTRAINT uq_scale_question UNIQUE (scale_id, question_id)
);

--rollback DROP TABLE tests.ScaleQuestion;
--rollback DROP TABLE methodologies.ScoreRange;
--rollback DROP TABLE methodologies.Scale;
--rollback DROP TABLE methodologies.Methodology;
--rollback DROP SCHEMA IF EXISTS methodologies CASCADE;