--liquibase formatted sql
--changeset ivantolkach:003-filling-admin-and-user
INSERT INTO users.admin(id, sname, fname, mname, email, password, phone_number, role, is_active)
VALUES
    ('e1cb5ed2-6850-4766-be54-75a8474cfb7c', 'Толкач', 'Иван', 'Вячеславович', 'barin@bntu.by', '$2a$12$KUH7ZNm03PMeY/OggsB2wOAIvyTwPlZmHPolNYp5SJ7i5kym2o8du', '+375291234567', 0, true);

INSERT INTO users.student(id, sname, fname, mname, faculty_id, group_number, gender, age, residence)
VALUES
    ('d122cdbd-36f1-4017-9b40-46729716980f', 'Бульбочка', 'Вячеслав', 'Дмитриевич', '00000000-0000-0000-0000-000000000007', 10701122,'MALE', 20, 'Общежитие'),
    ('06684181-5f21-4d2f-a7e9-0ec7e8b1b92b', 'Киянко', 'Максим', 'Витальевич', '00000000-0000-0000-0000-000000000007', 10701122,'MALE', 21, 'Общежитие'),
    ('8e3182e9-7476-487a-92d5-28cef9e36cee', 'Лиза', 'Пурр', 'Дмитриевна', '00000000-0000-0000-0000-000000000005', 10502323,'FEMALE', 20, 'Общежитие'),
    ('206295cc-6a3e-43df-9361-d495722a8fbd', 'Алексей', 'Мамонт', 'Валерьевич', '00000000-0000-0000-0000-000000000012', 11205124,'MALE', 19, 'Съёмное жильё');
--rollback DELETE FROM users.student
--rollback WHERE id IN (
--rollback   'd122cdbd-36f1-4017-9b40-46729716980f'
--rollback   '06684181-5f21-4d2f-a7e9-0ec7e8b1b92b',
--rollback   '8e3182e9-7476-487a-92d5-28cef9e36cee',
--rollback   '206295cc-6a3e-43df-9361-d495722a8fbd'
--rollback   );
--rollback DELETE FROM users.admin
--rollback WHERE id IN (
--rollback   'e1cb5ed2-6850-4766-be54-75a8474cfb7c'
--rollback   );
