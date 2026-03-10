--liquibase formatted sql
--changeset ivantolkach:001-init-users-schema
CREATE TABLE users.Faculty (
                               id UUID PRIMARY KEY,
                               name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE users.Student (
                               id UUID PRIMARY KEY,
                               sname VARCHAR(50) NOT NULL,
                               fname VARCHAR(50) NOT NULL,
                               mname VARCHAR(50),
                               faculty_id UUID NOT NULL,
                               group_number INTEGER NOT NULL CHECK (group_number >= 0 AND group_number <= 99999999),
                               gender VARCHAR(16) NOT NULL,
                               age INTEGER NOT NULL CHECK (age > 0 AND age <= 120),
                               residence VARCHAR(100) NOT NULL,
                               CONSTRAINT fk_student_faculty FOREIGN KEY (faculty_id) REFERENCES users.Faculty(id) ON DELETE RESTRICT ON UPDATE CASCADE
);


CREATE TABLE users.Admin (
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

--rollback DROP TABLE users.Admin;
--rollback DROP TABLE users.Student;
--rollback DROP TABLE users.Faculty;
--rollback DROP SCHEMA IF EXISTS users CASCADE;