--liquibase formatted sql
--changeset ivantolkach:001-init-schema
CREATE TABLE Faculty (
                         id UUID PRIMARY KEY,
                         name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE Student (
                         id UUID PRIMARY KEY,
                         sname VARCHAR(50) NOT NULL,
                         fname VARCHAR(50) NOT NULL,
                         mname VARCHAR(50),
                         faculty_id UUID NOT NULL,
                         group_number INTEGER NOT NULL CHECK (group_number >= 0 AND group_number <= 99999999),
                         gender VARCHAR(16) NOT NULL,
                         age INTEGER NOT NULL CHECK (age > 0 AND age <= 120),
                         residence VARCHAR(100) NOT NULL,
                         FOREIGN KEY (faculty_id) REFERENCES Faculty(id)
);

CREATE TABLE Admin (
                       id UUID PRIMARY KEY,
                       sname VARCHAR(50) NOT NULL,
                       fname VARCHAR(50) NOT NULL,
                       mname VARCHAR(50),
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       phone_number VARCHAR(20) NOT NULL,
                       role INTEGER DEFAULT 0,
                       is_active BOOLEAN DEFAULT FALSE
);

CREATE TABLE Methodology (
                             id UUID PRIMARY KEY,
                             name VARCHAR(100) NOT NULL UNIQUE,
                             description TEXT
);

CREATE TABLE Test (
                      id UUID PRIMARY KEY,
                      name VARCHAR(100) NOT NULL,
                      methodology_id UUID NOT NULL,
                      is_active BOOLEAN DEFAULT FALSE,
                      created_by UUID NOT NULL,
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      updated_by UUID,
                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      FOREIGN KEY (methodology_id) REFERENCES Methodology(id),
                      FOREIGN KEY (created_by) REFERENCES Admin(id),
                      FOREIGN KEY (updated_by) REFERENCES Admin(id)
);

CREATE TABLE Question (
                          id UUID PRIMARY KEY,
                          test_id UUID NOT NULL,
                          text TEXT NOT NULL,
                          type INTEGER DEFAULT 0,
                          position INTEGER NOT NULL,
                          FOREIGN KEY (test_id) REFERENCES Test(id)
);

CREATE TABLE Scale (
                       id UUID PRIMARY KEY,
                       methodology_id UUID NOT NULL,
                       name VARCHAR(100) NOT NULL,
                       is_total BOOLEAN DEFAULT FALSE,
                       description TEXT,
                       FOREIGN KEY (methodology_id) REFERENCES Methodology(id)
);

CREATE TABLE ScaleQuestion (
                               id UUID PRIMARY KEY,
                               scale_id UUID NOT NULL,
                               question_id UUID NOT NULL,
                               FOREIGN KEY (scale_id) REFERENCES Scale(id),
                               FOREIGN KEY (question_id) REFERENCES Question(id)
);

CREATE TABLE ScoreRange (
                            id UUID PRIMARY KEY,
                            scale_id UUID NOT NULL,
                            min_score INTEGER NOT NULL,
                            max_score INTEGER NOT NULL,
                            interpretation VARCHAR(50) NOT NULL,
                            description TEXT,
                            FOREIGN KEY (scale_id) REFERENCES Scale(id)
);

CREATE TABLE AnswerOption (
                              id UUID PRIMARY KEY,
                              question_id UUID NOT NULL,
                              text TEXT NOT NULL,
                              score INTEGER DEFAULT 0,
                              FOREIGN KEY (question_id) REFERENCES Question(id)
);

CREATE TABLE TestAttempt (
                             id UUID PRIMARY KEY,
                             student_id UUID NOT NULL,
                             test_id UUID NOT NULL,
                             attempt_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             FOREIGN KEY (student_id) REFERENCES Student(id),
                             FOREIGN KEY (test_id) REFERENCES Test(id)
);

CREATE TABLE TestAttemptScore (
                                  id UUID PRIMARY KEY,
                                  test_attempt_id UUID NOT NULL,
                                  scale_id UUID NOT NULL,
                                  score INTEGER NOT NULL,
                                  FOREIGN KEY (test_attempt_id) REFERENCES TestAttempt(id),
                                  FOREIGN KEY (scale_id) REFERENCES Scale(id)
);

CREATE TABLE StudentAnswer (
                               id UUID PRIMARY KEY,
                               test_attempt_id UUID NOT NULL,
                               question_id UUID NOT NULL,
                               selected_option_id UUID NOT NULL,
                               answer_value TEXT,
                               FOREIGN KEY (test_attempt_id) REFERENCES TestAttempt(id),
                               FOREIGN KEY (question_id) REFERENCES Question(id),
                               FOREIGN KEY (selected_option_id) REFERENCES AnswerOption(id)
);

--rollback DROP TABLE StudentAnswer;
--rollback DROP TABLE TestAttemptScore;
--rollback DROP TABLE TestAttempt;
--rollback DROP TABLE AnswerOption;
--rollback DROP TABLE ScoreRange;
--rollback DROP TABLE ScaleQuestion;
--rollback DROP TABLE Scale;
--rollback DROP TABLE Question;
--rollback DROP TABLE Test;
--rollback DROP TABLE Methodology;
--rollback DROP TABLE Admin;
--rollback DROP TABLE Student;
--rollback DROP TABLE Faculty;
