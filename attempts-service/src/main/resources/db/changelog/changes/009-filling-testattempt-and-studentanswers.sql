--liquibase formatted sql
--changeset ivantolkach:009-filling-testattempt-and-studentanswers
INSERT INTO attempts.testattempt(id, student_id, test_id, attempt_date)
VALUES
    ('606ecab5-c97c-4d11-8b3a-af2bfd64d184', 'd122cdbd-36f1-4017-9b40-46729716980f', '0435f8a9-4d5e-4e98-8052-ac09ca9a5670', '2026-03-03T14:09:09.1586071');


INSERT INTO attempts.studentanswer(id, test_attempt_id, question_id, answer_option_id, answer_value)
VALUES
    ('77e3511e-4036-40e6-b492-a1c9ffa825e2', '606ecab5-c97c-4d11-8b3a-af2bfd64d184', 'ea8b2e43-ab73-4933-89fc-4fc958f7226c', '14a5e4ce-433a-4e13-95e0-ae87e68390e0', 'none'),
    ('e7474683-87d2-41a2-ae8c-358213ae7ca9', '606ecab5-c97c-4d11-8b3a-af2bfd64d184', '559cf4ac-e794-4f6a-8ddc-9ffceebcecbf', '040d6647-b710-49f9-8d87-62e388c67065', 'none'),
    ('86e8b70f-58c5-43f1-8ab1-0954efc5c7ec', '606ecab5-c97c-4d11-8b3a-af2bfd64d184', '81c7c389-3015-44f1-9a37-3583cf73097c', 'cbef61a9-1f25-410b-a5cc-a94c354895fb', 'none'),
    ('70733f77-d70f-4bda-9f71-6d7c756e74b3', '606ecab5-c97c-4d11-8b3a-af2bfd64d184', 'ee00a954-adbf-46b2-a9b9-6ab6400e2499', '6f4de968-d99e-45d9-ba61-fb291525b165', 'none'),
    ('134c3bc2-479f-41ba-aad1-ab73d8a2aeec', '606ecab5-c97c-4d11-8b3a-af2bfd64d184', '464f434f-ac51-43ec-b24d-e32ebad59690', '72055f83-b842-4369-99af-f8f9161caed2', 'none'),
    ('bffda8c2-5baa-4621-ad92-3de7574d5358', '606ecab5-c97c-4d11-8b3a-af2bfd64d184', '5eeb32e5-19b0-47a8-814e-14a8e56e977c', '86f7d52c-54e4-4b1a-8097-fb22c6f8ca34', 'none'),
    ('effb7bf6-39fc-48b7-9233-83d18b72c4c7', '606ecab5-c97c-4d11-8b3a-af2bfd64d184', '03d38264-bb06-41f6-a804-d42fb94514ab', '25c87e9d-1ea7-438e-beae-0bbabcdf3665', 'none'),
    ('b71a693a-ceed-44de-aa5e-f75b95a87f76', '606ecab5-c97c-4d11-8b3a-af2bfd64d184', '369004e1-74dc-4089-b8d0-cf0e7900a6cd', 'b82943c9-31c5-42e8-99f5-fcef506cccd8', 'none'),
    ('2c0a5b4a-08df-4c83-b5f0-720a9c8a7675', '606ecab5-c97c-4d11-8b3a-af2bfd64d184', '312f96b4-cba4-4fc2-8e42-fa3441e9ebbc', '54142248-95eb-4aa6-99e7-ab3bfbdc4461', 'none'),
    ('1a8b7a5a-56c2-4021-a059-6984713cfe7e', '606ecab5-c97c-4d11-8b3a-af2bfd64d184', '24b9deef-d108-48f7-8b8e-dffb694e3b00', '48d17e81-ee71-4cbc-80e3-299e7ec603ca', 'none'),
    ('9090f4a0-8a96-43f3-b930-798f48be11a2', '606ecab5-c97c-4d11-8b3a-af2bfd64d184', 'fb380181-edf4-4e45-b63a-1815771ea311', '02d7b2fb-093d-4c45-8467-4e148f6ffbc6', 'none'),
    ('3abf309a-92f1-4dd4-a746-4614a3d5fbed', '606ecab5-c97c-4d11-8b3a-af2bfd64d184', '8afc8b90-4a86-40b5-872f-c51be5a3370b', '2bd785b1-8514-43c6-a00b-9b016ed04ec0', 'none'),
    ('c53fb275-18b6-4c35-810f-07764d797556', '606ecab5-c97c-4d11-8b3a-af2bfd64d184', '99e5c341-966d-4710-9389-50633174dd8c', 'caebfbd6-0472-4d8e-92bb-a04a8c1f39af', 'none'),
    ('5bbfd170-599e-44cc-baeb-222b6d8d4268', '606ecab5-c97c-4d11-8b3a-af2bfd64d184', '80f52397-8c36-4ce4-97df-2cf1ff47223f', '8c076a16-2975-4f8f-8d0b-b8e7da31ffd8', 'none'),
    ('cd8e0f43-e712-423e-93ce-509fbe94974f', '606ecab5-c97c-4d11-8b3a-af2bfd64d184', 'c1d71e2c-4423-430e-bf77-6f488a52c121', 'e5802dc9-1fd6-4e9f-9021-ade003156324', 'none'),
    ('700ec4e0-9b47-4025-a4a5-77fe419c72e5', '606ecab5-c97c-4d11-8b3a-af2bfd64d184', 'ee57ec2d-ea22-4ba5-a620-726ace5649d8', '0d95ae12-174c-43da-84b8-8ecc5ced6e6b', 'none'),
    ('d92a07ae-feb2-4c30-b25a-86be76452056', '606ecab5-c97c-4d11-8b3a-af2bfd64d184', '885be2c2-7889-4cd1-99ab-51c4749a4f87', 'd669f723-7e1e-4b19-823b-1eed1c4df355', 'none'),
    ('0605a9b0-bae3-4612-9c8a-976abb3d1989', '606ecab5-c97c-4d11-8b3a-af2bfd64d184', '8e77bd20-77db-43f0-a129-7ef901f13b98', 'd5ab1ca5-1b69-4008-a7df-cabae7ab2f30', 'none'),
    ('e58de5ff-8ff2-4116-a25b-b1a4a4302814', '606ecab5-c97c-4d11-8b3a-af2bfd64d184', 'd4090046-f923-4ba8-ad43-ef3d7b983a68', '8a045b24-b159-4f1d-829c-b9b6e97bd1f4', 'none'),
    ('db9a858c-f4bd-4861-a728-2e46ef66e0e2', '606ecab5-c97c-4d11-8b3a-af2bfd64d184', 'a66e467c-4bb9-46ee-aff7-8dd80bb1a5d7', '8074f5f2-a53f-40b5-aa73-f70f324801c5', 'none');

--rollback DELETE FROM attempts.studentanswer
--rollback WHERE id IN (
--rollback   '77e3511e-4036-40e6-b492-a1c9ffa825e2',
--rollback   'e7474683-87d2-41a2-ae8c-358213ae7ca9',
--rollback   '86e8b70f-58c5-43f1-8ab1-0954efc5c7ec',
--rollback   '70733f77-d70f-4bda-9f71-6d7c756e74b3',
--rollback   '134c3bc2-479f-41ba-aad1-ab73d8a2aeec',
--rollback   'bffda8c2-5baa-4621-ad92-3de7574d5358',
--rollback   'effb7bf6-39fc-48b7-9233-83d18b72c4c7',
--rollback   'b71a693a-ceed-44de-aa5e-f75b95a87f76',
--rollback   '2c0a5b4a-08df-4c83-b5f0-720a9c8a7675',
--rollback   '1a8b7a5a-56c2-4021-a059-6984713cfe7e',
--rollback   '9090f4a0-8a96-43f3-b930-798f48be11a2',
--rollback   '3abf309a-92f1-4dd4-a746-4614a3d5fbed',
--rollback   'c53fb275-18b6-4c35-810f-07764d797556',
--rollback   '5bbfd170-599e-44cc-baeb-222b6d8d4268',
--rollback   'cd8e0f43-e712-423e-93ce-509fbe94974f',
--rollback   '700ec4e0-9b47-4025-a4a5-77fe419c72e5',
--rollback   'd92a07ae-feb2-4c30-b25a-86be76452056',
--rollback   '0605a9b0-bae3-4612-9c8a-976abb3d1989',
--rollback   'e58de5ff-8ff2-4116-a25b-b1a4a4302814',
--rollback   'db9a858c-f4bd-4861-a728-2e46ef66e0e2'
--rollback   );
--rollback DELETE FROM attempts.testattempt
--rollback WHERE id IN (
--rollback   '606ecab5-c97c-4d11-8b3a-af2bfd64d184'
--rollback   );
