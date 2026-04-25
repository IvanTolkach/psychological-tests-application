--liquibase formatted sql
--changeset ivantolkach:001-init-methodologies-schema
--comment Сущности методик, шкал, диапазонов интерпретации и связи шкал с вопросами микросервиса methodologies-service

CREATE TABLE methodologies.Methodology (
                                           id UUID PRIMARY KEY,
                                           name VARCHAR(100) NOT NULL UNIQUE,
                                           description TEXT
);

COMMENT ON TABLE methodologies.Methodology IS 'Психологические методики (Шкала Бека, Шкала Горской, Анкета до 18 лет и др.)';
COMMENT ON COLUMN methodologies.Methodology.id IS 'Уникальный идентификатор методики';
COMMENT ON COLUMN methodologies.Methodology.name IS 'Название методики';
COMMENT ON COLUMN methodologies.Methodology.description IS 'Описание методики для администраторов';

CREATE TABLE methodologies.Scale (
                                     id UUID PRIMARY KEY,
                                     methodology_id UUID NOT NULL,
                                     name VARCHAR(100) NOT NULL,
                                     is_total BOOLEAN DEFAULT FALSE,
                                     description TEXT,
                                     CONSTRAINT fk_scale_methodology FOREIGN KEY (methodology_id) REFERENCES methodologies.Methodology(id) ON DELETE CASCADE ON UPDATE CASCADE
);

COMMENT ON TABLE methodologies.Scale IS 'Шкалы внутри методики с субфакторами (Тревожность, Фрустрация, Общая безнадёжность и т.д.)';
COMMENT ON COLUMN methodologies.Scale.id IS 'Уникальный идентификатор шкалы';
COMMENT ON COLUMN methodologies.Scale.methodology_id IS 'Методика, к которой относится шкала';
COMMENT ON COLUMN methodologies.Scale.name IS 'Название шкалы';
COMMENT ON COLUMN methodologies.Scale.is_total IS 'Это итоговая шкала методики (для подсчёта общего балла по всем шкалам методики)';
COMMENT ON COLUMN methodologies.Scale.description IS 'Описание шкалы (опционально)';

CREATE TABLE methodologies.ScoreRange (
                                          id UUID PRIMARY KEY,
                                          scale_id UUID NOT NULL,
                                          min_score INTEGER NOT NULL,
                                          max_score INTEGER NOT NULL,
                                          interpretation VARCHAR(255) NOT NULL,
                                          description TEXT,
                                          CONSTRAINT fk_scorerange_scale FOREIGN KEY (scale_id) REFERENCES methodologies.Scale(id) ON DELETE CASCADE ON UPDATE CASCADE
);

COMMENT ON TABLE methodologies.ScoreRange IS 'Диапазоны баллов и их интерпретации для каждой шкалы';
COMMENT ON COLUMN methodologies.ScoreRange.id IS 'Уникальный идентификатор диапазона';
COMMENT ON COLUMN methodologies.ScoreRange.scale_id IS 'Шкала, к которой относится диапазон';
COMMENT ON COLUMN methodologies.ScoreRange.min_score IS 'Минимальное значение диапазона';
COMMENT ON COLUMN methodologies.ScoreRange.max_score IS 'Максимальное значение диапазона';
COMMENT ON COLUMN methodologies.ScoreRange.interpretation IS 'Краткая интерпретация (низкий, средний, высокий, тяжёлая и т.д.)';
COMMENT ON COLUMN methodologies.ScoreRange.description IS 'Развёрнутое описание интерпретации (опционально)';

CREATE TABLE methodologies.ScaleQuestion (
                                     id UUID PRIMARY KEY,
                                     scale_id UUID NOT NULL,
                                     question_id UUID NOT NULL,
                                     CONSTRAINT fk_scalequestion_scale FOREIGN KEY (scale_id) REFERENCES methodologies.Scale(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                     CONSTRAINT uq_scale_question UNIQUE (scale_id, question_id)
);

COMMENT ON TABLE methodologies.ScaleQuestion IS 'Многие-ко-многим связь: какие вопросы входят в расчёт конкретной шкалы';
COMMENT ON COLUMN methodologies.ScaleQuestion.scale_id IS 'Шкала';
COMMENT ON COLUMN methodologies.ScaleQuestion.question_id IS 'Вопрос, который учитывается при подсчёте данной шкалы';

--rollback DROP TABLE tests.ScaleQuestion CASCADE;
--rollback DROP TABLE methodologies.ScoreRange CASCADE;
--rollback DROP TABLE methodologies.Scale CASCADE;
--rollback DROP TABLE methodologies.Methodology CASCADE;
--rollback DROP SCHEMA IF EXISTS methodologies CASCADE;