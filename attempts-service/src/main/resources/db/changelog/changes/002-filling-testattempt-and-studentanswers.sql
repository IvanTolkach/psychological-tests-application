--liquibase formatted sql
--changeset ivantolkach:002-filling-testattempt-and-studentanswers
--comment Начальное заполнение попыток и ответов студентов для тестирования программы

INSERT INTO attempts.testattempt(id, student_id, test_id, attempt_date)
VALUES
    ('606ecab5-c97c-4d11-8b3a-af2bfd64d184', 'd122cdbd-36f1-4017-9b40-46729716980f', '0435f8a9-4d5e-4e98-8052-ac09ca9a5670', '2026-03-03T14:09:09.1586071'),
    ('bc152c24-5bdd-4513-bb15-8c216ceac8c4', '06684181-5f21-4d2f-a7e9-0ec7e8b1b92b', '0435f8a9-4d5e-4e98-8052-ac09ca9a5670', '2026-03-04T14:09:09.1586071'),
    ('28e2cd1b-bdcf-4cd2-a53e-5aa333f4ed02', '0fe54f9b-d33d-4b7d-b8dc-98931c0e36a7', '0435f8a9-4d5e-4e98-8052-ac09ca9a5670', '2026-03-04T14:09:09.1586071'),
    ('3a6c8429-a029-4894-86cf-108f08c27020', '8a897069-2d5a-40c3-a0a1-455e34571ca1', '0435f8a9-4d5e-4e98-8052-ac09ca9a5670', '2026-03-04T14:09:10.1586071'),
    ('4a1c7dfc-dd28-4633-b979-7f168b9defa1', 'a2a2119b-3da3-4a55-98f3-931683d2663a', '0435f8a9-4d5e-4e98-8052-ac09ca9a5670', '2026-03-04T14:09:11.1586071'),
    ('ecf26b01-336e-41e8-8b24-b60cede127e1', 'fe318ea8-0bda-4b2e-b0a6-1c444205a663', '0435f8a9-4d5e-4e98-8052-ac09ca9a5670', '2026-03-04T14:09:12.1586071'),
    ('cd159da2-fec2-4487-8ed1-07ce3fb58440', 'b752c191-a856-4f7b-8138-cfa599db274d', '0435f8a9-4d5e-4e98-8052-ac09ca9a5670', '2026-03-04T14:09:13.1586071'),
    ('31283184-fda7-4f56-bf16-f722fd309bcc', '1ad3c035-d337-461e-94bb-f7720b1e7ed0', '0435f8a9-4d5e-4e98-8052-ac09ca9a5670', '2026-03-04T14:09:14.1586071'),
    ('7029c041-7c66-438b-b489-2141a813e7ac', '8e3182e9-7476-487a-92d5-28cef9e36cee', '0435f8a9-4d5e-4e98-8052-ac09ca9a5670', '2026-03-04T14:09:09.1586071'),
    ('d4bb534e-2db7-411f-991c-b57c8e170b9c', '206295cc-6a3e-43df-9361-d495722a8fbd', '0435f8a9-4d5e-4e98-8052-ac09ca9a5670', '2026-03-05T14:09:09.1586071'),
    ('a602097d-bd2e-4aed-a1c3-4e487369c3e3', 'caed8a7c-2d0e-4dc7-ae73-aa5a593d9e73', '0435f8a9-4d5e-4e98-8052-ac09ca9a5670', '2026-03-05T14:09:15.1586071'),

    ('05e0781e-4d1e-4119-b14a-5e88dcc570e7', 'd122cdbd-36f1-4017-9b40-46729716980f', '31d97957-1b91-444d-97b2-d9e735a856ce', '2026-02-03T14:09:09.1586071'),
    ('6041c794-7c81-4bca-8247-9ce06261a888', '06684181-5f21-4d2f-a7e9-0ec7e8b1b92b', '31d97957-1b91-444d-97b2-d9e735a856ce', '2026-02-04T14:09:09.1586071'),
    ('12a5a01f-6631-4350-80f1-ccc8f98edb9f', '0fe54f9b-d33d-4b7d-b8dc-98931c0e36a7', '31d97957-1b91-444d-97b2-d9e735a856ce', '2026-02-04T14:09:09.1586071'),
    ('8b33a423-d6fe-448a-8358-2c6d657091f1', '8a897069-2d5a-40c3-a0a1-455e34571ca1', '31d97957-1b91-444d-97b2-d9e735a856ce', '2026-02-04T14:09:10.1586071'),
    ('204a6738-f683-497f-a568-e2381e2ce622', 'a2a2119b-3da3-4a55-98f3-931683d2663a', '31d97957-1b91-444d-97b2-d9e735a856ce', '2026-02-04T14:09:11.1586071'),
    ('3b5d2e8d-32cb-4d1b-9e15-51793c1c2075', 'fe318ea8-0bda-4b2e-b0a6-1c444205a663', '31d97957-1b91-444d-97b2-d9e735a856ce', '2026-02-04T14:09:12.1586071'),
    ('413de1ea-76ad-4a0f-b53c-e1bec31f9775', 'b752c191-a856-4f7b-8138-cfa599db274d', '31d97957-1b91-444d-97b2-d9e735a856ce', '2026-02-04T14:09:13.1586071'),
    ('4d2c596f-4f76-4bae-b54f-fe18bc940dfc', '1ad3c035-d337-461e-94bb-f7720b1e7ed0', '31d97957-1b91-444d-97b2-d9e735a856ce', '2026-02-04T14:09:14.1586071'),
    ('5a04c944-e2ea-4b5d-b9bb-e6ddc20297cf', '8e3182e9-7476-487a-92d5-28cef9e36cee', '31d97957-1b91-444d-97b2-d9e735a856ce', '2026-02-04T14:09:09.1586071'),
    ('687d2562-a346-49a8-8de9-78c2c4667d37', '206295cc-6a3e-43df-9361-d495722a8fbd', '31d97957-1b91-444d-97b2-d9e735a856ce', '2026-02-05T14:09:09.1586071'),
    ('31843e57-0268-430d-919b-920d3a4f17c5', 'caed8a7c-2d0e-4dc7-ae73-aa5a593d9e73', '31d97957-1b91-444d-97b2-d9e735a856ce', '2026-02-05T14:09:15.1586071'),

    ('d7ec102e-4cba-4938-904e-a54f0562fee9', 'd122cdbd-36f1-4017-9b40-46729716980f', 'f825d758-7f5a-4194-ac2b-7c2428cfbd94', '2026-01-03T14:09:09.1586071'),
    ('b1ec749e-53dc-4e0a-acab-7cf7a04aee99', '06684181-5f21-4d2f-a7e9-0ec7e8b1b92b', 'f825d758-7f5a-4194-ac2b-7c2428cfbd94', '2026-01-04T14:09:09.1586071'),
    ('5fbac8d5-855b-4910-b6e2-980f6b444ad7', '0fe54f9b-d33d-4b7d-b8dc-98931c0e36a7', 'f825d758-7f5a-4194-ac2b-7c2428cfbd94', '2026-01-04T14:09:09.1586071'),
    ('754d48f9-fafd-4531-89c4-63378bd6e674', '8a897069-2d5a-40c3-a0a1-455e34571ca1', 'f825d758-7f5a-4194-ac2b-7c2428cfbd94', '2026-01-04T14:09:10.1586071'),
    ('ac4b1032-a35b-4a18-ba41-c1a8b8d46144', 'a2a2119b-3da3-4a55-98f3-931683d2663a', 'f825d758-7f5a-4194-ac2b-7c2428cfbd94', '2026-01-04T14:09:11.1586071'),
    ('bf442394-f007-4538-bab5-4bea1564c50f', 'fe318ea8-0bda-4b2e-b0a6-1c444205a663', 'f825d758-7f5a-4194-ac2b-7c2428cfbd94', '2026-01-04T14:09:12.1586071'),
    ('ecb527c5-ff9a-4892-94b2-a416a5fd14b4', 'b752c191-a856-4f7b-8138-cfa599db274d', 'f825d758-7f5a-4194-ac2b-7c2428cfbd94', '2026-01-04T14:09:13.1586071'),
    ('40e81607-d7d0-4d83-9d24-01f4a7edcfaf', '1ad3c035-d337-461e-94bb-f7720b1e7ed0', 'f825d758-7f5a-4194-ac2b-7c2428cfbd94', '2026-01-04T14:09:14.1586071'),
    ('f4f3f12d-d871-4e1d-a786-cc7b8f16dd1b', '8e3182e9-7476-487a-92d5-28cef9e36cee', 'f825d758-7f5a-4194-ac2b-7c2428cfbd94', '2026-01-04T14:09:09.1586071'),
    ('bf5b9468-7724-4c02-901e-5fcb477c093b', '206295cc-6a3e-43df-9361-d495722a8fbd', 'f825d758-7f5a-4194-ac2b-7c2428cfbd94', '2026-01-05T14:09:09.1586071'),
    ('8629642c-ac5e-4ae5-8085-36a752ea9867', 'caed8a7c-2d0e-4dc7-ae73-aa5a593d9e73', 'f825d758-7f5a-4194-ac2b-7c2428cfbd94', '2026-01-05T14:09:15.1586071');


INSERT INTO attempts.StudentAnswer (
    id,
    test_attempt_id,
    question_id,
    answer_option_id,
    answer_value
)
SELECT DISTINCT ON (ta.id, q.id)
    gen_random_uuid(),
    ta.id,
    q.id,
    ao.id,
    NULL
FROM attempts.TestAttempt ta
         JOIN tests.Question q
              ON q.test_id = ta.test_id
         JOIN tests.AnswerOption ao
              ON ao.question_id = q.id
ORDER BY
    ta.id,
    q.id,
    RANDOM();

--rollback TRUNCATE TABLE attempts.studentanswer;
--rollback DELETE FROM attempts.testattempt
--rollback WHERE id IN (
--rollback   '606ecab5-c97c-4d11-8b3a-af2bfd64d184',
--rollback   'bc152c24-5bdd-4513-bb15-8c216ceac8c4',
--rollback   '28e2cd1b-bdcf-4cd2-a53e-5aa333f4ed02',
--rollback   '3a6c8429-a029-4894-86cf-108f08c27020',
--rollback   '4a1c7dfc-dd28-4633-b979-7f168b9defa1',
--rollback   'ecf26b01-336e-41e8-8b24-b60cede127e1',
--rollback   'cd159da2-fec2-4487-8ed1-07ce3fb58440',
--rollback   '31283184-fda7-4f56-bf16-f722fd309bcc',
--rollback   '7029c041-7c66-438b-b489-2141a813e7ac',
--rollback   'd4bb534e-2db7-411f-991c-b57c8e170b9c',
--rollback   'a602097d-bd2e-4aed-a1c3-4e487369c3e3',
--rollback   '05e0781e-4d1e-4119-b14a-5e88dcc570e7',
--rollback   '6041c794-7c81-4bca-8247-9ce06261a888',
--rollback   '12a5a01f-6631-4350-80f1-ccc8f98edb9f',
--rollback   '8b33a423-d6fe-448a-8358-2c6d657091f1',
--rollback   '204a6738-f683-497f-a568-e2381e2ce622',
--rollback   '3b5d2e8d-32cb-4d1b-9e15-51793c1c2075',
--rollback   '413de1ea-76ad-4a0f-b53c-e1bec31f9775',
--rollback   '4d2c596f-4f76-4bae-b54f-fe18bc940dfc',
--rollback   '5a04c944-e2ea-4b5d-b9bb-e6ddc20297cf',
--rollback   '687d2562-a346-49a8-8de9-78c2c4667d37',
--rollback   '31843e57-0268-430d-919b-920d3a4f17c5',
--rollback   'd7ec102e-4cba-4938-904e-a54f0562fee9',
--rollback   'b1ec749e-53dc-4e0a-acab-7cf7a04aee99',
--rollback   '5fbac8d5-855b-4910-b6e2-980f6b444ad7',
--rollback   '754d48f9-fafd-4531-89c4-63378bd6e674',
--rollback   'ac4b1032-a35b-4a18-ba41-c1a8b8d46144',
--rollback   'bf442394-f007-4538-bab5-4bea1564c50f',
--rollback   'ecb527c5-ff9a-4892-94b2-a416a5fd14b4',
--rollback   '40e81607-d7d0-4d83-9d24-01f4a7edcfaf',
--rollback   'f4f3f12d-d871-4e1d-a786-cc7b8f16dd1b',
--rollback   'bf5b9468-7724-4c02-901e-5fcb477c093b',
--rollback   '8629642c-ac5e-4ae5-8085-36a752ea9867'
--rollback   );
