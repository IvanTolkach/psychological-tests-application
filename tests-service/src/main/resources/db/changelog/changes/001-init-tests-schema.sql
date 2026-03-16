--liquibase formatted sql
--changeset ivantolkach:001-init-tests-schema
--comment Создание сущностей тестов, вопросов и вариантов ответов микросервиса tests-service

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

COMMENT ON TABLE tests.Test IS 'Конкретные тесты / анкеты, доступные для прохождения студентами. Связаны с методикой и автором-создателем';
COMMENT ON COLUMN tests.Test.id IS 'Уникальный идентификатор теста';
COMMENT ON COLUMN tests.Test.name IS 'Название теста (например: "Шкала безнадёжности Бека", "ТСП 1-2 курс", "Анкета до 18 лет")';
COMMENT ON COLUMN tests.Test.methodology_id IS 'Ссылка на методику, к которой относится тест';
COMMENT ON COLUMN tests.Test.is_active IS 'Тест доступен студентам для прохождения';
COMMENT ON COLUMN tests.Test.created_by IS 'Администратор, создавший тест';
COMMENT ON COLUMN tests.Test.created_at IS 'Дата и время создания теста';
COMMENT ON COLUMN tests.Test.updated_by IS 'Администратор, внёсший последние изменения';
COMMENT ON COLUMN tests.Test.updated_at IS 'Дата и время последнего изменения';

CREATE TABLE tests.Question (
                                id UUID PRIMARY KEY,
                                test_id UUID NOT NULL,
                                text TEXT NOT NULL,
                                type INTEGER DEFAULT 0,
                                position INTEGER NOT NULL,
                                CONSTRAINT fk_question_test FOREIGN KEY (test_id) REFERENCES tests.Test(id) ON DELETE CASCADE ON UPDATE CASCADE
);

COMMENT ON TABLE tests.Question IS 'Вопросы, входящие в состав конкретного теста';
COMMENT ON COLUMN tests.Question.id IS 'Уникальный идентификатор вопроса';
COMMENT ON COLUMN tests.Question.test_id IS 'Тест, к которому относится вопрос';
COMMENT ON COLUMN tests.Question.text IS 'Текст вопроса';
COMMENT ON COLUMN tests.Question.type IS 'Тип ответа (0=одиночный выбор, 1=множественный, 2=вопрос с выпадающим списком, 3=линейная шкала, 4=рейтинг и т.д.)';
COMMENT ON COLUMN tests.Question.position IS 'Порядковый номер вопроса в тесте (для вывода в определённом порядке)';

CREATE TABLE tests.AnswerOption (
                                    id UUID PRIMARY KEY,
                                    question_id UUID NOT NULL,
                                    text TEXT NOT NULL,
                                    score INTEGER DEFAULT 0,
                                    CONSTRAINT fk_answeroption_question FOREIGN KEY (question_id) REFERENCES tests.Question(id) ON DELETE CASCADE ON UPDATE CASCADE
);

COMMENT ON TABLE tests.AnswerOption IS 'Варианты ответа на вопрос с привязанными баллами для подсчёта';
COMMENT ON COLUMN tests.AnswerOption.id IS 'Уникальный идентификатор варианта ответа';
COMMENT ON COLUMN tests.AnswerOption.question_id IS 'Вопрос, к которому относится вариант';
COMMENT ON COLUMN tests.AnswerOption.text IS 'Текст варианта ответа ("Верно", "Никогда", и т.д.)';
COMMENT ON COLUMN tests.AnswerOption.score IS 'Балл, который добавляется при выборе этого варианта';

ALTER TABLE methodologies.ScaleQuestion
    ADD CONSTRAINT fk_scalequestion_question
        FOREIGN KEY (question_id) REFERENCES tests.Question(id)
            ON DELETE RESTRICT ON UPDATE CASCADE;

COMMENT ON CONSTRAINT fk_scalequestion_question ON methodologies.ScaleQuestion IS 'Связь вопросов с шкалами методики (многие-ко-многим)';

--rollback ALTER TABLE methodologies.ScaleQuestion DROP CONSTRAINT IF EXISTS fk_scalequestion_question;
--rollback DROP TABLE tests.AnswerOption CASCADE;
--rollback DROP TABLE tests.Question CASCADE;
--rollback DROP TABLE tests.Test CASCADE;
--rollback DROP SCHEMA IF EXISTS tests CASCADE;