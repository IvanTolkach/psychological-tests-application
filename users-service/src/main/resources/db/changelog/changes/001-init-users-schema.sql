--liquibase formatted sql
--changeset ivantolkach:001-init-users-schema
--comment Создание сущностей факультетов, студентов и администраторов микросервиса users-service (факультеты, студенты, администраторы)

CREATE TABLE users.Faculty (
                               id UUID PRIMARY KEY,
                               name VARCHAR(100) NOT NULL UNIQUE
);

COMMENT ON TABLE users.Faculty IS 'Справочник факультетов университета. Хранит предустановленный список факультетов университета для выбора студентами';
COMMENT ON COLUMN users.Faculty.id IS 'Уникальный идентификатор факультета';
COMMENT ON COLUMN users.Faculty.name IS 'Сокращённое название факультета, например "ФИТР"';

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

COMMENT ON TABLE users.Student IS 'Основная сущность студента. Хранит анкетные данные студентов, собранные перед тестированием';
COMMENT ON COLUMN users.Student.id IS 'Уникальный идентификатор студента';
COMMENT ON COLUMN users.Student.sname IS 'Фамилия';
COMMENT ON COLUMN users.Student.fname IS 'Имя';
COMMENT ON COLUMN users.Student.mname IS 'Отчество (может быть NULL)';
COMMENT ON COLUMN users.Student.faculty_id IS 'Ссылка на факультет студента';
COMMENT ON COLUMN users.Student.group_number IS 'Номер учебной группы (8-значный код)';
COMMENT ON COLUMN users.Student.gender IS 'Пол студента';
COMMENT ON COLUMN users.Student.age IS 'Возраст на момент заполнения анкеты';
COMMENT ON COLUMN users.Student.residence IS 'Место проживания (общежитие / с родителями / съёмное жильё / другое)';


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

COMMENT ON TABLE users.Admin  IS 'Учётные записи администраторов (психологи, методисты). Роли: STANDARD / SUPER';
COMMENT ON COLUMN users.Admin.id IS 'Уникальный идентификатор администратора';
COMMENT ON COLUMN users.Admin.email IS 'Email — используется для входа. Уникален';
COMMENT ON COLUMN users.Admin.password IS 'Хэш пароля';
COMMENT ON COLUMN users.Admin.role IS 'Уровень прав: 0=STANDARD, 1=SUPER';
COMMENT ON COLUMN users.Admin.is_active IS 'Аккаунт активирован (SUPER может активировать других)';

--rollback DROP TABLE users.Admin CASCADE;
--rollback DROP TABLE users.Student CASCADE;
--rollback DROP TABLE users.Faculty CASCADE;
--rollback DROP SCHEMA IF EXISTS users CASCADE;