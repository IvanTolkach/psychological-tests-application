--liquibase formatted sql
--changeset ivantolkach:002-filling-methodology-scales-and-scoreranges
INSERT INTO methodologies.methodology(id, name, description)
VALUES
    ('c8efd2f4-3f75-4b01-af91-87d9f6ace6ca', 'Методика Горской', 'Пример описания методики.');


INSERT INTO methodologies.scale(id, methodology_id, name, is_total, description)
VALUES
    ('24deebaf-6ab0-4694-b0df-fed125f03d56', 'c8efd2f4-3f75-4b01-af91-87d9f6ace6ca', 'Шкала тревожности', false, 'Определяет уровень способности индивида к ощущению тревоги.'),
    ('5d2412dd-6740-4a02-8c95-0adf03f0b7be', 'c8efd2f4-3f75-4b01-af91-87d9f6ace6ca', 'Шкала фрустрации', false, 'Определяет показатель психического состояния, которое возникает из-за реальных или мнимых препятствий, которые мешают достижению цели.'),
    ('4f328c48-36ec-4821-a774-e8753b98b97a', 'c8efd2f4-3f75-4b01-af91-87d9f6ace6ca', 'Общая шкала', true, 'Определяет уровень склонности к суицидальному поведению.');


INSERT INTO methodologies.scorerange(id, scale_id, min_score, max_score, interpretation, description)
VALUES
    ('f77875c7-a2d1-4c95-ae83-a5b1f942ad03', '24deebaf-6ab0-4694-b0df-fed125f03d56', 0, 7, 'Низкий уровень тревожности', 'Пример описания.'),
    ('c7bdeb6a-f3d5-4dbd-a863-fd7efed61057', '24deebaf-6ab0-4694-b0df-fed125f03d56', 8, 11, 'Средний уровень тревожности', 'Пример описания.'),
    ('ab72480b-729e-4385-8b8d-5dc19bbd3ec5', '24deebaf-6ab0-4694-b0df-fed125f03d56', 12, 16, 'Высокий уровень тревожности', 'Пример описания.'),
    ('0e23d627-db0e-42c6-bd25-6a3da7f06b32', '24deebaf-6ab0-4694-b0df-fed125f03d56', 17, 20, 'Очень высокий уровень тревожности', 'Пример описания.'),

    ('f35a7f40-f758-490d-8460-d7c33e1109c7', '5d2412dd-6740-4a02-8c95-0adf03f0b7be', 0, 7, 'Низкий уровень фрустрации', 'Пример описания.'),
    ('c4189dc2-22f1-43d7-8f5c-66befa6b49fe', '5d2412dd-6740-4a02-8c95-0adf03f0b7be', 8, 9, 'Средний уровень фрустрации', 'Пример описания.'),
    ('dba0d21d-e580-4623-8b3c-d42df6bef984', '5d2412dd-6740-4a02-8c95-0adf03f0b7be', 10, 15, 'Высокий уровень фрустрации', 'Пример описания.'),
    ('61aa9240-76c4-46bc-9bf9-f484a68f6cf4', '5d2412dd-6740-4a02-8c95-0adf03f0b7be', 16, 20, 'Очень высокий уровень фрустрации', 'Пример описания.'),

    ('797b01d6-043c-41d4-b72f-74b18d33507b', '4f328c48-36ec-4821-a774-e8753b98b97a', 0, 38, 'Уровень склонности к суицидальному поведению низкий', 'Пример описания.'),
    ('bc7f0432-5371-45d1-94f0-464abcb3a7cc', '4f328c48-36ec-4821-a774-e8753b98b97a', 39, 45, 'Уровень склонности к суицидальному поведению находится в норме', 'Пример описания.'),
    ('db122c04-a9bd-4e51-8633-99b423810c74', '4f328c48-36ec-4821-a774-e8753b98b97a', 46, 999, 'Уровень склонности к суицидальному поведению высок, нужна коррекционная работа', 'Пример описания.');

--rollback DELETE FROM methodologies.scorerange
--rollback WHERE id IN (
--rollback   'f77875c7-a2d1-4c95-ae83-a5b1f942ad03',
--rollback   'c7bdeb6a-f3d5-4dbd-a863-fd7efed61057',
--rollback   'ab72480b-729e-4385-8b8d-5dc19bbd3ec5',
--rollback   '0e23d627-db0e-42c6-bd25-6a3da7f06b32',
--rollback   'f35a7f40-f758-490d-8460-d7c33e1109c7',
--rollback   'c4189dc2-22f1-43d7-8f5c-66befa6b49fe',
--rollback   'dba0d21d-e580-4623-8b3c-d42df6bef984',
--rollback   '61aa9240-76c4-46bc-9bf9-f484a68f6cf4',
--rollback   '797b01d6-043c-41d4-b72f-74b18d33507b',
--rollback   'bc7f0432-5371-45d1-94f0-464abcb3a7cc',
--rollback   'db122c04-a9bd-4e51-8633-99b423810c74'
--rollback   );
--rollback DELETE FROM methodologies.scale
--rollback WHERE id IN (
--rollback   '24deebaf-6ab0-4694-b0df-fed125f03d56',
--rollback   '5d2412dd-6740-4a02-8c95-0adf03f0b7be',
--rollback   '4f328c48-36ec-4821-a774-e8753b98b97a'
--rollback   );
--rollback DELETE FROM methodologies.methodology
--rollback WHERE id IN (
--rollback   'c8efd2f4-3f75-4b01-af91-87d9f6ace6ca'
--rollback   );
