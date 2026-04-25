--liquibase formatted sql
--changeset ivantolkach:002-filling-methodology-scales-and-scoreranges
--comment Начальное заполнение методик, описанных в техническом задании

INSERT INTO methodologies.methodology(id, name)
VALUES
    ('c8efd2f4-3f75-4b01-af91-87d9f6ace6ca', 'Методика Горской'),
    ('a086fd91-77bb-42dd-a0d1-a100f58800e6', 'Методика Бекка'),
    ('db7d2d7c-2f74-4aed-b380-abdf27ccb6ee', 'Методика отношения к ПАВ');


INSERT INTO methodologies.scale(id, methodology_id, name, is_total, description)
VALUES
    ('24deebaf-6ab0-4694-b0df-fed125f03d56', 'c8efd2f4-3f75-4b01-af91-87d9f6ace6ca', 'Шкала тревожности', false, 'Определяет уровень способности индивида к ощущению тревоги.'),
    ('5d2412dd-6740-4a02-8c95-0adf03f0b7be', 'c8efd2f4-3f75-4b01-af91-87d9f6ace6ca', 'Шкала фрустрации', false, 'Определяет показатель психического состояния, которое возникает из-за реальных или мнимых препятствий, которые мешают достижению цели.'),
    ('454668ed-d910-4768-87e4-fe1f6ae80b4c', 'c8efd2f4-3f75-4b01-af91-87d9f6ace6ca', 'Шкала агрессии', false, 'Определяет повышенную психологическую активность, стремление к лидерству с применением силы по отношению к другим людям. для суицидентов допускается снижение агрессивности от 10 до 0.'),
    ('6e4abf6f-43dd-46a2-80e8-31e789fc923f', 'c8efd2f4-3f75-4b01-af91-87d9f6ace6ca', 'Шкала ригидности', false, 'Затруднения в изменении определенной деятельности в условиях, которые объективно нуждаются в изменении. Для лиц с суицидальным поведением - 13 баллов и выше.'),
    ('4f328c48-36ec-4821-a774-e8753b98b97a', 'c8efd2f4-3f75-4b01-af91-87d9f6ace6ca', 'Общая шкала', true, 'Определяет уровень склонности к суицидальному поведению.'),

    ('103b9a98-ca43-4630-a32c-ab80f086d443', 'a086fd91-77bb-42dd-a0d1-a100f58800e6', 'Шкала безнадёжности', true, 'Предназначена для предсказания возможности самоубийства на основе мыслей о будущем, надежд и представляет собой 20 утверждений, которые отражают чувства, состояния, отношение к будущему и прошлому.'),

    ('bf090ca6-bfa0-4fc6-b219-be5506dad7b2', 'db7d2d7c-2f74-4aed-b380-abdf27ccb6ee', 'Шкала семейного фактора', false, null),
    ('53bdd692-bcbc-4e47-ad90-b1d47dfcfd55', 'db7d2d7c-2f74-4aed-b380-abdf27ccb6ee', 'Шкала психологического фактора', false, null),
    ('b18d6958-d623-4ee0-b088-67556c68da01', 'db7d2d7c-2f74-4aed-b380-abdf27ccb6ee', 'Шкала фактора ближайшего окружения', false, null),
    ('f98a21e7-fef2-483e-8962-64ce83fe1eab', 'db7d2d7c-2f74-4aed-b380-abdf27ccb6ee', 'Шкала образовательного фактора', false, null),
    ('f54a5455-1fc5-48fd-80d7-19efccda3464', 'db7d2d7c-2f74-4aed-b380-abdf27ccb6ee', 'Шкала общего риска', true, null);


INSERT INTO methodologies.scorerange(id, scale_id, min_score, max_score, interpretation)
VALUES
    ('f77875c7-a2d1-4c95-ae83-a5b1f942ad03', '24deebaf-6ab0-4694-b0df-fed125f03d56', 0, 7, 'Низкий уровень тревожности'),
    ('c7bdeb6a-f3d5-4dbd-a863-fd7efed61057', '24deebaf-6ab0-4694-b0df-fed125f03d56', 8, 11, 'Средний уровень тревожности'),
    ('ab72480b-729e-4385-8b8d-5dc19bbd3ec5', '24deebaf-6ab0-4694-b0df-fed125f03d56', 12, 16, 'Высокий уровень тревожности'),
    ('0e23d627-db0e-42c6-bd25-6a3da7f06b32', '24deebaf-6ab0-4694-b0df-fed125f03d56', 17, 20, 'Очень высокий уровень тревожности'),

    ('f35a7f40-f758-490d-8460-d7c33e1109c7', '5d2412dd-6740-4a02-8c95-0adf03f0b7be', 0, 7, 'Низкий уровень фрустрации'),
    ('c4189dc2-22f1-43d7-8f5c-66befa6b49fe', '5d2412dd-6740-4a02-8c95-0adf03f0b7be', 8, 9, 'Средний уровень фрустрации'),
    ('dba0d21d-e580-4623-8b3c-d42df6bef984', '5d2412dd-6740-4a02-8c95-0adf03f0b7be', 10, 15, 'Высокий уровень фрустрации'),
    ('61aa9240-76c4-46bc-9bf9-f484a68f6cf4', '5d2412dd-6740-4a02-8c95-0adf03f0b7be', 16, 20, 'Очень высокий уровень фрустрации'),

    ('613ba338-999a-434b-a9a8-35d2c37f5c82', '454668ed-d910-4768-87e4-fe1f6ae80b4c', 0, 10, 'Низкий уровень агрессивности'),
    ('fb0607cd-b4a6-4cac-8748-ae6b47d2a067', '454668ed-d910-4768-87e4-fe1f6ae80b4c', 11, 12, 'Средний уровень агрессивности'),
    ('129c3fce-7ee4-4578-9701-d4daaee1259f', '454668ed-d910-4768-87e4-fe1f6ae80b4c', 13, 16, 'Высокий уровень агрессивности'),
    ('98debb02-e1b8-4668-9cf9-af63e1eacef4', '454668ed-d910-4768-87e4-fe1f6ae80b4c', 17, 20, 'Очень высокий уровень агрессивности'),

    ('1a8baf4e-e15d-4eb4-a2d1-6d5aa1931c89', '6e4abf6f-43dd-46a2-80e8-31e789fc923f', 0, 10, 'Низкий уровень ригидности'),
    ('86838fac-cc78-43ea-a47b-f3ac372eface', '6e4abf6f-43dd-46a2-80e8-31e789fc923f', 11, 12, 'Средний уровень ригидности'),
    ('8b15ceb3-d8de-4153-be7d-030527f1ddd7', '6e4abf6f-43dd-46a2-80e8-31e789fc923f', 13, 16, 'Высокий уровень ригидности'),
    ('9557782f-81be-459b-8b4b-2b82bd4e6030', '6e4abf6f-43dd-46a2-80e8-31e789fc923f', 17, 20, 'Очень высокий уровень ригидности'),

    ('797b01d6-043c-41d4-b72f-74b18d33507b', '4f328c48-36ec-4821-a774-e8753b98b97a', 0, 38, 'Уровень склонности к суицидальному поведению низкий'),
    ('bc7f0432-5371-45d1-94f0-464abcb3a7cc', '4f328c48-36ec-4821-a774-e8753b98b97a', 39, 45, 'Уровень склонности к суицидальному поведению находится в норме'),
    ('db122c04-a9bd-4e51-8633-99b423810c74', '4f328c48-36ec-4821-a774-e8753b98b97a', 46, 999, 'Уровень склонности к суицидальному поведению высок, нужна коррекционная работа'),


    ('ddd66072-7980-4f27-b5a2-bfb7841ad312', '103b9a98-ca43-4630-a32c-ab80f086d443', 0, 3, 'Безнадёжность не выявлена'),
    ('3db88af0-61a0-43ae-b229-b25f940690c1', '103b9a98-ca43-4630-a32c-ab80f086d443', 4, 8, 'Безнадёжность лёгкая'),
    ('94454b11-0691-473f-9c76-1ba5d091fe22', '103b9a98-ca43-4630-a32c-ab80f086d443', 9, 14, 'Безнадёжность умеренная'),
    ('54a09cff-5edc-4a14-9370-b78566a530dc', '103b9a98-ca43-4630-a32c-ab80f086d443', 15, 20, 'Безнадёжность тяжелая'),


    ('3fef212f-5c22-4121-b912-33d98de931d7', 'bf090ca6-bfa0-4fc6-b219-be5506dad7b2', 34, 46, 'Высокая степень риска'),
    ('51d588e5-45ef-4ebb-98a2-226f3c8995f1', 'bf090ca6-bfa0-4fc6-b219-be5506dad7b2', 18, 33, 'Средняя степень риска'),
    ('09a0aedf-2567-4094-b14b-d217bab3f856', 'bf090ca6-bfa0-4fc6-b219-be5506dad7b2', 6, 7, 'Низкая степень риска'),

    ('16b20a41-c6b7-4b41-aead-262f21ffaf00', '53bdd692-bcbc-4e47-ad90-b1d47dfcfd55', 38, 48, 'Высокая степень риска'),
    ('b66e04a3-1c40-4541-8251-16f313dbe80c', '53bdd692-bcbc-4e47-ad90-b1d47dfcfd55', 17, 37, 'Средняя степень риска'),
    ('f3962aff-16e5-4a5c-8fd6-685ea2484af4', '53bdd692-bcbc-4e47-ad90-b1d47dfcfd55', 6, 16, 'Низкая степень риска'),

    ('bc44add5-4ccf-4260-9c03-522460b6e88d', 'b18d6958-d623-4ee0-b088-67556c68da01', 43, 60, 'Высокая степень риска'),
    ('aabbb357-9798-43c8-980f-ae7433a39882', 'b18d6958-d623-4ee0-b088-67556c68da01', 23, 42, 'Средняя степень риска'),
    ('8cb98dfa-656d-414a-b893-62917c576a78', 'b18d6958-d623-4ee0-b088-67556c68da01', 8, 22, 'Низкая степень риска'),

    ('c3a269b7-d5f8-41a2-841b-a75af9b8d90f', 'f98a21e7-fef2-483e-8962-64ce83fe1eab', 17, 22, 'Высокая степень риска'),
    ('28125f22-d36a-4df8-a547-bcd11589a7ec', 'f98a21e7-fef2-483e-8962-64ce83fe1eab', 11, 16, 'Средняя степень риска'),
    ('557311dc-a49d-4e44-9919-e87e1f94e990', 'f98a21e7-fef2-483e-8962-64ce83fe1eab', 6, 10, 'Низкая степень риска'),

    ('0426516a-79a6-43ec-add7-3392fefc899e', 'f54a5455-1fc5-48fd-80d7-19efccda3464', 129, 176, 'Высокая степень риска'),
    ('2acbf48f-43c6-4f78-8922-9e2260cd1528', 'f54a5455-1fc5-48fd-80d7-19efccda3464', 66, 128, 'Средняя степень риска'),
    ('851b2a1a-699b-43f4-8de4-0c33a20c3d48', 'f54a5455-1fc5-48fd-80d7-19efccda3464', 26, 65, 'Низкая степень риска');

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
--rollback   'db122c04-a9bd-4e51-8633-99b423810c74',
--rollback   '613ba338-999a-434b-a9a8-35d2c37f5c82',
--rollback   'fb0607cd-b4a6-4cac-8748-ae6b47d2a067',
--rollback   '129c3fce-7ee4-4578-9701-d4daaee1259f',
--rollback   '98debb02-e1b8-4668-9cf9-af63e1eacef4',
--rollback   '1a8baf4e-e15d-4eb4-a2d1-6d5aa1931c89',
--rollback   '86838fac-cc78-43ea-a47b-f3ac372eface',
--rollback   '8b15ceb3-d8de-4153-be7d-030527f1ddd7',
--rollback   '9557782f-81be-459b-8b4b-2b82bd4e6030',
--rollback   '797b01d6-043c-41d4-b72f-74b18d33507b',
--rollback   'bc7f0432-5371-45d1-94f0-464abcb3a7cc',
--rollback   'db122c04-a9bd-4e51-8633-99b423810c74',
--rollback   'ddd66072-7980-4f27-b5a2-bfb7841ad312',
--rollback   '3db88af0-61a0-43ae-b229-b25f940690c1',
--rollback   '94454b11-0691-473f-9c76-1ba5d091fe22',
--rollback   '54a09cff-5edc-4a14-9370-b78566a530dc',
--rollback   '3fef212f-5c22-4121-b912-33d98de931d7',
--rollback   '51d588e5-45ef-4ebb-98a2-226f3c8995f1',
--rollback   '09a0aedf-2567-4094-b14b-d217bab3f856',
--rollback   '16b20a41-c6b7-4b41-aead-262f21ffaf00',
--rollback   'b66e04a3-1c40-4541-8251-16f313dbe80c',
--rollback   'f3962aff-16e5-4a5c-8fd6-685ea2484af4',
--rollback   'bc44add5-4ccf-4260-9c03-522460b6e88d',
--rollback   'aabbb357-9798-43c8-980f-ae7433a39882',
--rollback   '8cb98dfa-656d-414a-b893-62917c576a78',
--rollback   'c3a269b7-d5f8-41a2-841b-a75af9b8d90f',
--rollback   '28125f22-d36a-4df8-a547-bcd11589a7ec',
--rollback   '557311dc-a49d-4e44-9919-e87e1f94e990',
--rollback   '0426516a-79a6-43ec-add7-3392fefc899e',
--rollback   '2acbf48f-43c6-4f78-8922-9e2260cd1528',
--rollback   '851b2a1a-699b-43f4-8de4-0c33a20c3d48'
--rollback   );
--rollback DELETE FROM methodologies.scale
--rollback WHERE id IN (
--rollback   '24deebaf-6ab0-4694-b0df-fed125f03d56',
--rollback   '5d2412dd-6740-4a02-8c95-0adf03f0b7be',
--rollback   '454668ed-d910-4768-87e4-fe1f6ae80b4c',
--rollback   '6e4abf6f-43dd-46a2-80e8-31e789fc923f',
--rollback   '4f328c48-36ec-4821-a774-e8753b98b97a',
--rollback   '103b9a98-ca43-4630-a32c-ab80f086d443',
--rollback   'bf090ca6-bfa0-4fc6-b219-be5506dad7b2',
--rollback   '53bdd692-bcbc-4e47-ad90-b1d47dfcfd55',
--rollback   'b18d6958-d623-4ee0-b088-67556c68da01',
--rollback   'f98a21e7-fef2-483e-8962-64ce83fe1eab',
--rollback   'f54a5455-1fc5-48fd-80d7-19efccda3464'
--rollback   );
--rollback DELETE FROM methodologies.methodology
--rollback WHERE id IN (
--rollback   'c8efd2f4-3f75-4b01-af91-87d9f6ace6ca',
--rollback   'a086fd91-77bb-42dd-a0d1-a100f58800e6',
--rollback   'db7d2d7c-2f74-4aed-b380-abdf27ccb6ee'
--rollback   );
