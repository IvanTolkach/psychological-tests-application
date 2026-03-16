--liquibase formatted sql
--changeset ivantolkach:002-filling-test-questions-answeroptions-and-scalequestions
--comment Начальное заполнение тестов, описанных в техническом задании

INSERT INTO tests.test(id, name, methodology_id, is_active, created_by, created_at, updated_by, updated_at)
VALUES
    ('0435f8a9-4d5e-4e98-8052-ac09ca9a5670', 'Тест 1-2 курсы', 'c8efd2f4-3f75-4b01-af91-87d9f6ace6ca', false, 'e1cb5ed2-6850-4766-be54-75a8474cfb7c', '2026-03-03T14:08:09.1586071', 'e1cb5ed2-6850-4766-be54-75a8474cfb7c', '2026-03-03T14:08:09.1586071');


INSERT INTO tests.question(id, test_id, text, type, "position")
VALUES
    ('ea8b2e43-ab73-4933-89fc-4fc958f7226c', '0435f8a9-4d5e-4e98-8052-ac09ca9a5670', 'Я часто неуверен в своих силах.', 0, 1),
    ('559cf4ac-e794-4f6a-8ddc-9ffceebcecbf', '0435f8a9-4d5e-4e98-8052-ac09ca9a5670', 'Я часто краснею из-за пустяков.', 0, 2),
    ('81c7c389-3015-44f1-9a37-3583cf73097c', '0435f8a9-4d5e-4e98-8052-ac09ca9a5670', 'Я часто просыпаюсь ночью.', 0, 3),
    ('ee00a954-adbf-46b2-a9b9-6ab6400e2499', '0435f8a9-4d5e-4e98-8052-ac09ca9a5670', 'Я легко впадаю в отчаяние.', 0, 4),
    ('464f434f-ac51-43ec-b24d-e32ebad59690', '0435f8a9-4d5e-4e98-8052-ac09ca9a5670', 'Меня волнуют даже мнимые неприятности.', 0, 5),
    ('5eeb32e5-19b0-47a8-814e-14a8e56e977c', '0435f8a9-4d5e-4e98-8052-ac09ca9a5670', 'Меня пугают трудности, с которыми я буду встречаться в жизни.', 0, 6),
    ('03d38264-bb06-41f6-a804-d42fb94514ab', '0435f8a9-4d5e-4e98-8052-ac09ca9a5670', 'Я часто обращаю внимание на свои недостатки.', 0, 7),
    ('369004e1-74dc-4089-b8d0-cf0e7900a6cd', '0435f8a9-4d5e-4e98-8052-ac09ca9a5670', 'Меня легко убедить.', 0, 8),
    ('312f96b4-cba4-4fc2-8e42-fa3441e9ebbc', '0435f8a9-4d5e-4e98-8052-ac09ca9a5670', 'Меня волнует состояние моего здоровья.', 0, 9),
    ('24b9deef-d108-48f7-8b8e-dffb694e3b00', '0435f8a9-4d5e-4e98-8052-ac09ca9a5670', 'Я тяжело переношу время ожидания.', 0, 10),
    ('fb380181-edf4-4e45-b63a-1815771ea311', '0435f8a9-4d5e-4e98-8052-ac09ca9a5670', 'Нередко мне кажется безвыходным положение, из которого можно было бы найти выход.', 0, 11),
    ('8afc8b90-4a86-40b5-872f-c51be5a3370b', '0435f8a9-4d5e-4e98-8052-ac09ca9a5670', 'Неприятности меня часто огорчают, и я падаю духом.', 0, 12),
    ('99e5c341-966d-4710-9389-50633174dd8c', '0435f8a9-4d5e-4e98-8052-ac09ca9a5670', 'При больших неприятностях я беру вину на себя.', 0, 13),
    ('80f52397-8c36-4ce4-97df-2cf1ff47223f', '0435f8a9-4d5e-4e98-8052-ac09ca9a5670', 'Несчастья и неудачи ни чему меня не учат.', 0, 14),
    ('c1d71e2c-4423-430e-bf77-6f488a52c121', '0435f8a9-4d5e-4e98-8052-ac09ca9a5670', 'Я часто отказываюсь от борьбы потому, что считаю её напрасной.', 0, 15),
    ('ee57ec2d-ea22-4ba5-a620-726ace5649d8', '0435f8a9-4d5e-4e98-8052-ac09ca9a5670', 'Нередко я чувствую себя беззащитным.', 0, 16),
    ('885be2c2-7889-4cd1-99ab-51c4749a4f87', '0435f8a9-4d5e-4e98-8052-ac09ca9a5670', 'Иногда у меня бывает подавленное настроение.', 0, 17),
    ('8e77bd20-77db-43f0-a129-7ef901f13b98', '0435f8a9-4d5e-4e98-8052-ac09ca9a5670', 'Я чувствую растерянность, когда у меня возникают трудности.', 0, 18),
    ('d4090046-f923-4ba8-ad43-ef3d7b983a68', '0435f8a9-4d5e-4e98-8052-ac09ca9a5670', 'В тяжелую минуту я иногда веду себя как ребенок.', 0, 19),
    ('a66e467c-4bb9-46ee-aff7-8dd80bb1a5d7', '0435f8a9-4d5e-4e98-8052-ac09ca9a5670', 'Я думаю, что никогда не смогу исправить свои недостатки.', 0, 20);


INSERT INTO tests.answeroption(id, question_id, text, score)
VALUES
    ('14a5e4ce-433a-4e13-95e0-ae87e68390e0', 'ea8b2e43-ab73-4933-89fc-4fc958f7226c', 'Подходит', 2),
    ('11215baf-848f-4f34-8a35-7234edf201e0', 'ea8b2e43-ab73-4933-89fc-4fc958f7226c', 'Не совсем подходит', 1),
    ('e6744ec7-6130-4c5a-9b66-5c55f55301b2', 'ea8b2e43-ab73-4933-89fc-4fc958f7226c', 'Не подходит', 0),

    ('040d6647-b710-49f9-8d87-62e388c67065', '559cf4ac-e794-4f6a-8ddc-9ffceebcecbf', 'Подходит', 2),
    ('667dbb15-2c9c-432e-872b-18b6296a911a', '559cf4ac-e794-4f6a-8ddc-9ffceebcecbf', 'Не совсем подходит', 1),
    ('6f0cb2a2-d4a0-4187-a881-e46c91edb06d', '559cf4ac-e794-4f6a-8ddc-9ffceebcecbf', 'Не подходит', 0),

    ('cbef61a9-1f25-410b-a5cc-a94c354895fb', '81c7c389-3015-44f1-9a37-3583cf73097c', 'Подходит', 2),
    ('c6dc32da-f653-49a6-9da4-cf3a9b636ee6', '81c7c389-3015-44f1-9a37-3583cf73097c', 'Не совсем подходит', 1),
    ('b19ecfbf-0a5e-4676-a561-7970f7558528', '81c7c389-3015-44f1-9a37-3583cf73097c', 'Не подходит', 0),

    ('6f4de968-d99e-45d9-ba61-fb291525b165', 'ee00a954-adbf-46b2-a9b9-6ab6400e2499', 'Подходит', 2),
    ('cc1f33e7-ab19-4634-9f26-944f7588a1ff', 'ee00a954-adbf-46b2-a9b9-6ab6400e2499', 'Не совсем подходит', 1),
    ('634b9405-1b91-43c0-a917-d2e890c6712f', 'ee00a954-adbf-46b2-a9b9-6ab6400e2499', 'Не подходит', 0),

    ('72055f83-b842-4369-99af-f8f9161caed2', '464f434f-ac51-43ec-b24d-e32ebad59690', 'Подходит', 2),
    ('11bf6b62-375b-4c97-9ee5-f647c7ff5750', '464f434f-ac51-43ec-b24d-e32ebad59690', 'Не совсем подходит', 1),
    ('3f5678ee-f011-4c9d-a7fc-0ad16ae02f9b', '464f434f-ac51-43ec-b24d-e32ebad59690', 'Не подходит', 0),

    ('86f7d52c-54e4-4b1a-8097-fb22c6f8ca34', '5eeb32e5-19b0-47a8-814e-14a8e56e977c', 'Подходит', 2),
    ('919f32cf-ba54-4a8f-a5bc-9b643ac0f558', '5eeb32e5-19b0-47a8-814e-14a8e56e977c', 'Не совсем подходит', 1),
    ('8fd3bbc0-2fbf-488a-87dd-8b9197d09b1a', '5eeb32e5-19b0-47a8-814e-14a8e56e977c', 'Не подходит', 0),

    ('25c87e9d-1ea7-438e-beae-0bbabcdf3665', '03d38264-bb06-41f6-a804-d42fb94514ab', 'Подходит', 2),
    ('91fe74dc-d43e-40bb-954c-5db4bd8374c7', '03d38264-bb06-41f6-a804-d42fb94514ab', 'Не совсем подходит', 1),
    ('e40f6f1a-0002-4260-9106-bfae37c9a450', '03d38264-bb06-41f6-a804-d42fb94514ab', 'Не подходит', 0),

    ('b82943c9-31c5-42e8-99f5-fcef506cccd8', '369004e1-74dc-4089-b8d0-cf0e7900a6cd', 'Подходит', 2),
    ('d2a527e9-25e5-46da-8be3-605249d9e802', '369004e1-74dc-4089-b8d0-cf0e7900a6cd', 'Не совсем подходит', 1),
    ('6ed9deb2-83ba-47f4-ace0-fdaf399570eb', '369004e1-74dc-4089-b8d0-cf0e7900a6cd', 'Не подходит', 0),

    ('54142248-95eb-4aa6-99e7-ab3bfbdc4461', '312f96b4-cba4-4fc2-8e42-fa3441e9ebbc', 'Подходит', 2),
    ('e1159266-d92d-4790-9eee-0e8e1a1993d5', '312f96b4-cba4-4fc2-8e42-fa3441e9ebbc', 'Не совсем подходит', 1),
    ('e86554f3-1572-48e2-afbb-fa81e2222dfc', '312f96b4-cba4-4fc2-8e42-fa3441e9ebbc', 'Не подходит', 0),

    ('cdc225e1-d537-459e-a1ab-266deaa490c9', '24b9deef-d108-48f7-8b8e-dffb694e3b00', 'Подходит', 2),
    ('48d17e81-ee71-4cbc-80e3-299e7ec603ca', '24b9deef-d108-48f7-8b8e-dffb694e3b00', 'Не совсем подходит', 1),
    ('959409da-3e72-48e9-9943-3675a2a20f3f', '24b9deef-d108-48f7-8b8e-dffb694e3b00', 'Не подходит', 0),

    ('0f30ad11-284d-4c99-b5bf-b3bff63d7849', 'fb380181-edf4-4e45-b63a-1815771ea311', 'Подходит', 2),
    ('2e08d60b-8fc9-461e-baee-2e272ecb3f8e', 'fb380181-edf4-4e45-b63a-1815771ea311', 'Не совсем подходит', 1),
    ('02d7b2fb-093d-4c45-8467-4e148f6ffbc6', 'fb380181-edf4-4e45-b63a-1815771ea311', 'Не подходит', 0),

    ('6b19b9e9-64d6-4b6e-ae53-4d177e608b6e', '8afc8b90-4a86-40b5-872f-c51be5a3370b', 'Подходит', 2),
    ('e85534e2-2ec7-4a59-9ebe-24b009b22a63', '8afc8b90-4a86-40b5-872f-c51be5a3370b', 'Не совсем подходит', 1),
    ('2bd785b1-8514-43c6-a00b-9b016ed04ec0', '8afc8b90-4a86-40b5-872f-c51be5a3370b', 'Не подходит', 0),

    ('3e42fcac-3cec-4a67-b450-fa9fba0c5595', '99e5c341-966d-4710-9389-50633174dd8c', 'Подходит', 2),
    ('31e61383-e6ca-41e9-9a3c-aff4c20e3356', '99e5c341-966d-4710-9389-50633174dd8c', 'Не совсем подходит', 1),
    ('caebfbd6-0472-4d8e-92bb-a04a8c1f39af', '99e5c341-966d-4710-9389-50633174dd8c', 'Не подходит', 0),

    ('72b1ca31-136b-4a0d-8a7d-e25aaa5042cf', '80f52397-8c36-4ce4-97df-2cf1ff47223f', 'Подходит', 2),
    ('4fd469a0-dacb-44cf-ae14-ea37331def71', '80f52397-8c36-4ce4-97df-2cf1ff47223f', 'Не совсем подходит', 1),
    ('8c076a16-2975-4f8f-8d0b-b8e7da31ffd8', '80f52397-8c36-4ce4-97df-2cf1ff47223f', 'Не подходит', 0),

    ('a3c1320e-99fe-4888-a5e3-e2ff15b166de', 'c1d71e2c-4423-430e-bf77-6f488a52c121', 'Подходит', 2),
    ('c6182af9-bf04-4dc3-ae30-20b7d22fc4ae', 'c1d71e2c-4423-430e-bf77-6f488a52c121', 'Не совсем подходит', 1),
    ('e5802dc9-1fd6-4e9f-9021-ade003156324', 'c1d71e2c-4423-430e-bf77-6f488a52c121', 'Не подходит', 0),

    ('e2b19f39-7bd3-480e-8f62-bac495949b99', 'ee57ec2d-ea22-4ba5-a620-726ace5649d8', 'Подходит', 2),
    ('d5538c35-e539-4857-aeba-0858c4d62ab7', 'ee57ec2d-ea22-4ba5-a620-726ace5649d8', 'Не совсем подходит', 1),
    ('0d95ae12-174c-43da-84b8-8ecc5ced6e6b', 'ee57ec2d-ea22-4ba5-a620-726ace5649d8', 'Не подходит', 0),

    ('2bacd270-f3c3-4f60-86ad-6fdb3bc0bf9a', '885be2c2-7889-4cd1-99ab-51c4749a4f87', 'Подходит', 2),
    ('c14ec8a5-2c8b-4b83-b617-0777ebc40863', '885be2c2-7889-4cd1-99ab-51c4749a4f87', 'Не совсем подходит', 1),
    ('d669f723-7e1e-4b19-823b-1eed1c4df355', '885be2c2-7889-4cd1-99ab-51c4749a4f87', 'Не подходит', 0),

    ('d3660ef1-e32a-43e7-9d7c-9584fbb90ad3', '8e77bd20-77db-43f0-a129-7ef901f13b98', 'Подходит', 2),
    ('2cbb73a7-5bcb-4add-8e68-d4e855db98d2', '8e77bd20-77db-43f0-a129-7ef901f13b98', 'Не совсем подходит', 1),
    ('d5ab1ca5-1b69-4008-a7df-cabae7ab2f30', '8e77bd20-77db-43f0-a129-7ef901f13b98', 'Не подходит', 0),

    ('a9d81c98-92a6-4005-a9e7-bcbbf0e96467', 'd4090046-f923-4ba8-ad43-ef3d7b983a68', 'Подходит', 2),
    ('f6c195cc-3974-4159-b6fb-f39cb810cea3', 'd4090046-f923-4ba8-ad43-ef3d7b983a68', 'Не совсем подходит', 1),
    ('8a045b24-b159-4f1d-829c-b9b6e97bd1f4', 'd4090046-f923-4ba8-ad43-ef3d7b983a68', 'Не подходит', 0),

    ('f95c7813-7c5c-4598-a556-5749a7c472d0', 'a66e467c-4bb9-46ee-aff7-8dd80bb1a5d7', 'Подходит', 2),
    ('c9fe8313-872d-45df-bdb5-0d8240538314', 'a66e467c-4bb9-46ee-aff7-8dd80bb1a5d7', 'Не совсем подходит', 1),
    ('8074f5f2-a53f-40b5-aa73-f70f324801c5', 'a66e467c-4bb9-46ee-aff7-8dd80bb1a5d7', 'Не подходит', 0);


INSERT INTO methodologies.scalequestion(id, scale_id, question_id)
VALUES
    ('afdad6e3-32be-4c3e-8cf5-8169cc364df4', '24deebaf-6ab0-4694-b0df-fed125f03d56', 'ea8b2e43-ab73-4933-89fc-4fc958f7226c'),
    ('482e112a-7d4f-412c-981b-12470c2c0363', '24deebaf-6ab0-4694-b0df-fed125f03d56', '559cf4ac-e794-4f6a-8ddc-9ffceebcecbf'),
    ('6ea7df11-c8e0-4056-abb3-4ef0bbf41cad', '24deebaf-6ab0-4694-b0df-fed125f03d56', '81c7c389-3015-44f1-9a37-3583cf73097c'),
    ('3649cfb0-a383-4118-b774-5546c155bb08', '24deebaf-6ab0-4694-b0df-fed125f03d56', 'ee00a954-adbf-46b2-a9b9-6ab6400e2499'),
    ('4c607e4c-ef7d-4648-a687-76120c01f63d', '24deebaf-6ab0-4694-b0df-fed125f03d56', '464f434f-ac51-43ec-b24d-e32ebad59690'),
    ('48e7a46a-b8cc-4d5f-b7ed-ac20a0f48fbf', '24deebaf-6ab0-4694-b0df-fed125f03d56', '5eeb32e5-19b0-47a8-814e-14a8e56e977c'),
    ('1c55c619-8745-45e6-8490-c78191831d4a', '24deebaf-6ab0-4694-b0df-fed125f03d56', '03d38264-bb06-41f6-a804-d42fb94514ab'),
    ('4aafecf6-a380-4367-b993-11ec127a8225', '24deebaf-6ab0-4694-b0df-fed125f03d56', '369004e1-74dc-4089-b8d0-cf0e7900a6cd'),
    ('5cddf830-0cf9-4018-98fe-0d8ab1c190ad', '24deebaf-6ab0-4694-b0df-fed125f03d56', '312f96b4-cba4-4fc2-8e42-fa3441e9ebbc'),
    ('50d9f263-026f-40d5-ab2e-bd1d37f0e948', '24deebaf-6ab0-4694-b0df-fed125f03d56', '24b9deef-d108-48f7-8b8e-dffb694e3b00'),
    ('32deb911-55c9-411a-88d3-78413e1d08b8', '5d2412dd-6740-4a02-8c95-0adf03f0b7be', 'fb380181-edf4-4e45-b63a-1815771ea311'),
    ('201bef78-42f4-46c2-a040-b137542b2db0', '5d2412dd-6740-4a02-8c95-0adf03f0b7be', '8afc8b90-4a86-40b5-872f-c51be5a3370b'),
    ('081a7240-70bc-4b75-ac6b-068fd4279294', '5d2412dd-6740-4a02-8c95-0adf03f0b7be', '99e5c341-966d-4710-9389-50633174dd8c'),
    ('c125a44e-68da-46b9-96b0-e6d6bedb3833', '5d2412dd-6740-4a02-8c95-0adf03f0b7be', '80f52397-8c36-4ce4-97df-2cf1ff47223f'),
    ('b5cb313f-9218-48b4-a0c3-9ae181cdd27d', '5d2412dd-6740-4a02-8c95-0adf03f0b7be', 'c1d71e2c-4423-430e-bf77-6f488a52c121'),
    ('467564d9-8ea9-4004-9400-62d01e066458', '5d2412dd-6740-4a02-8c95-0adf03f0b7be', 'ee57ec2d-ea22-4ba5-a620-726ace5649d8'),
    ('883e7cdd-d3db-4a84-83df-33fd1a191348', '5d2412dd-6740-4a02-8c95-0adf03f0b7be', '885be2c2-7889-4cd1-99ab-51c4749a4f87'),
    ('75ad14db-9f28-4c03-97fe-86cce3175e07', '5d2412dd-6740-4a02-8c95-0adf03f0b7be', '8e77bd20-77db-43f0-a129-7ef901f13b98'),
    ('84a8e877-1f2e-4ba8-9e52-9bdc6303e759', '5d2412dd-6740-4a02-8c95-0adf03f0b7be', 'd4090046-f923-4ba8-ad43-ef3d7b983a68'),
    ('105ee6d8-7682-490a-9ccb-5de40c918738', '5d2412dd-6740-4a02-8c95-0adf03f0b7be', 'a66e467c-4bb9-46ee-aff7-8dd80bb1a5d7');

--rollback DELETE FROM methodologies.scalequestion
--rollback WHERE id IN (
--rollback   'afdad6e3-32be-4c3e-8cf5-8169cc364df4',
--rollback   '482e112a-7d4f-412c-981b-12470c2c0363',
--rollback   '6ea7df11-c8e0-4056-abb3-4ef0bbf41cad',
--rollback   '3649cfb0-a383-4118-b774-5546c155bb08',
--rollback   '4c607e4c-ef7d-4648-a687-76120c01f63d',
--rollback   '48e7a46a-b8cc-4d5f-b7ed-ac20a0f48fbf',
--rollback   '1c55c619-8745-45e6-8490-c78191831d4a',
--rollback   '4aafecf6-a380-4367-b993-11ec127a8225',
--rollback   '5cddf830-0cf9-4018-98fe-0d8ab1c190ad',
--rollback   '50d9f263-026f-40d5-ab2e-bd1d37f0e948',
--rollback   '32deb911-55c9-411a-88d3-78413e1d08b8',
--rollback   '201bef78-42f4-46c2-a040-b137542b2db0',
--rollback   '081a7240-70bc-4b75-ac6b-068fd4279294',
--rollback   'c125a44e-68da-46b9-96b0-e6d6bedb3833',
--rollback   'b5cb313f-9218-48b4-a0c3-9ae181cdd27d',
--rollback   '467564d9-8ea9-4004-9400-62d01e066458',
--rollback   '883e7cdd-d3db-4a84-83df-33fd1a191348',
--rollback   '75ad14db-9f28-4c03-97fe-86cce3175e07',
--rollback   '84a8e877-1f2e-4ba8-9e52-9bdc6303e759',
--rollback   '105ee6d8-7682-490a-9ccb-5de40c918738'
--rollback   );
--rollback DELETE FROM tests.answeroption
--rollback WHERE id IN (
--rollback   '14a5e4ce-433a-4e13-95e0-ae87e68390e0',
--rollback   '11215baf-848f-4f34-8a35-7234edf201e0',
--rollback   'e6744ec7-6130-4c5a-9b66-5c55f55301b2',
--rollback   '040d6647-b710-49f9-8d87-62e388c67065',
--rollback   '667dbb15-2c9c-432e-872b-18b6296a911a',
--rollback   '6f0cb2a2-d4a0-4187-a881-e46c91edb06d',
--rollback   'cbef61a9-1f25-410b-a5cc-a94c354895fb',
--rollback   'c6dc32da-f653-49a6-9da4-cf3a9b636ee6',
--rollback   'b19ecfbf-0a5e-4676-a561-7970f7558528',
--rollback   '6f4de968-d99e-45d9-ba61-fb291525b165',
--rollback   'cc1f33e7-ab19-4634-9f26-944f7588a1ff',
--rollback   '634b9405-1b91-43c0-a917-d2e890c6712f',
--rollback   '72055f83-b842-4369-99af-f8f9161caed2',
--rollback   '11bf6b62-375b-4c97-9ee5-f647c7ff5750',
--rollback   '3f5678ee-f011-4c9d-a7fc-0ad16ae02f9b',
--rollback   '86f7d52c-54e4-4b1a-8097-fb22c6f8ca34',
--rollback   '919f32cf-ba54-4a8f-a5bc-9b643ac0f558',
--rollback   '8fd3bbc0-2fbf-488a-87dd-8b9197d09b1a',
--rollback   '25c87e9d-1ea7-438e-beae-0bbabcdf3665',
--rollback   '91fe74dc-d43e-40bb-954c-5db4bd8374c7',
--rollback   'e40f6f1a-0002-4260-9106-bfae37c9a450',
--rollback   'b82943c9-31c5-42e8-99f5-fcef506cccd8',
--rollback   'd2a527e9-25e5-46da-8be3-605249d9e802',
--rollback   '6ed9deb2-83ba-47f4-ace0-fdaf399570eb',
--rollback   '54142248-95eb-4aa6-99e7-ab3bfbdc4461',
--rollback   'e1159266-d92d-4790-9eee-0e8e1a1993d5',
--rollback   'e86554f3-1572-48e2-afbb-fa81e2222dfc',
--rollback   'cdc225e1-d537-459e-a1ab-266deaa490c9',
--rollback   '48d17e81-ee71-4cbc-80e3-299e7ec603ca',
--rollback   '959409da-3e72-48e9-9943-3675a2a20f3f',
--rollback   '0f30ad11-284d-4c99-b5bf-b3bff63d7849',
--rollback   '2e08d60b-8fc9-461e-baee-2e272ecb3f8e',
--rollback   '02d7b2fb-093d-4c45-8467-4e148f6ffbc6',
--rollback   '6b19b9e9-64d6-4b6e-ae53-4d177e608b6e',
--rollback   'e85534e2-2ec7-4a59-9ebe-24b009b22a63',
--rollback   '2bd785b1-8514-43c6-a00b-9b016ed04ec0',
--rollback   '3e42fcac-3cec-4a67-b450-fa9fba0c5595',
--rollback   '31e61383-e6ca-41e9-9a3c-aff4c20e3356',
--rollback   'caebfbd6-0472-4d8e-92bb-a04a8c1f39af',
--rollback   '72b1ca31-136b-4a0d-8a7d-e25aaa5042cf',
--rollback   '4fd469a0-dacb-44cf-ae14-ea37331def71',
--rollback   '8c076a16-2975-4f8f-8d0b-b8e7da31ffd8',
--rollback   'a3c1320e-99fe-4888-a5e3-e2ff15b166de',
--rollback   'c6182af9-bf04-4dc3-ae30-20b7d22fc4ae',
--rollback   'e5802dc9-1fd6-4e9f-9021-ade003156324',
--rollback   'e2b19f39-7bd3-480e-8f62-bac495949b99',
--rollback   'd5538c35-e539-4857-aeba-0858c4d62ab7',
--rollback   '0d95ae12-174c-43da-84b8-8ecc5ced6e6b',
--rollback   '2bacd270-f3c3-4f60-86ad-6fdb3bc0bf9a',
--rollback   'c14ec8a5-2c8b-4b83-b617-0777ebc40863',
--rollback   'd669f723-7e1e-4b19-823b-1eed1c4df355',
--rollback   'd3660ef1-e32a-43e7-9d7c-9584fbb90ad3',
--rollback   '2cbb73a7-5bcb-4add-8e68-d4e855db98d2',
--rollback   'd5ab1ca5-1b69-4008-a7df-cabae7ab2f30',
--rollback   'a9d81c98-92a6-4005-a9e7-bcbbf0e96467',
--rollback   'f6c195cc-3974-4159-b6fb-f39cb810cea3',
--rollback   '8a045b24-b159-4f1d-829c-b9b6e97bd1f4',
--rollback   'f95c7813-7c5c-4598-a556-5749a7c472d0',
--rollback   'c9fe8313-872d-45df-bdb5-0d8240538314',
--rollback   '8074f5f2-a53f-40b5-aa73-f70f324801c5'
--rollback   );
--rollback DELETE FROM tests.question
--rollback WHERE id IN (
--rollback   'ea8b2e43-ab73-4933-89fc-4fc958f7226c',
--rollback   '559cf4ac-e794-4f6a-8ddc-9ffceebcecbf',
--rollback   '81c7c389-3015-44f1-9a37-3583cf73097c',
--rollback   'ee00a954-adbf-46b2-a9b9-6ab6400e2499',
--rollback   '464f434f-ac51-43ec-b24d-e32ebad59690',
--rollback   '5eeb32e5-19b0-47a8-814e-14a8e56e977c',
--rollback   '03d38264-bb06-41f6-a804-d42fb94514ab',
--rollback   '369004e1-74dc-4089-b8d0-cf0e7900a6cd',
--rollback   '312f96b4-cba4-4fc2-8e42-fa3441e9ebbc',
--rollback   '24b9deef-d108-48f7-8b8e-dffb694e3b00',
--rollback   'fb380181-edf4-4e45-b63a-1815771ea311',
--rollback   '8afc8b90-4a86-40b5-872f-c51be5a3370b',
--rollback   '99e5c341-966d-4710-9389-50633174dd8c',
--rollback   '80f52397-8c36-4ce4-97df-2cf1ff47223f',
--rollback   'c1d71e2c-4423-430e-bf77-6f488a52c121',
--rollback   'ee57ec2d-ea22-4ba5-a620-726ace5649d8',
--rollback   '885be2c2-7889-4cd1-99ab-51c4749a4f87',
--rollback   '8e77bd20-77db-43f0-a129-7ef901f13b98',
--rollback   'd4090046-f923-4ba8-ad43-ef3d7b983a68',
--rollback   'a66e467c-4bb9-46ee-aff7-8dd80bb1a5d7'
--rollback   );
--rollback DELETE FROM tests.test
--rollback WHERE id IN (
--rollback   '0435f8a9-4d5e-4e98-8052-ac09ca9a5670'
--rollback   );
