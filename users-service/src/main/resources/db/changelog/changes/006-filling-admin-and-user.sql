--liquibase formatted sql
--changeset ivantolkach:006-filling-admin-and-user
INSERT INTO users.admin(id, sname, fname, mname, email, password, phone_number, role, is_active)
VALUES
    ('e1cb5ed2-6850-4766-be54-75a8474cfb7c', 'Толкач', 'Иван', 'Вячеславович', 'barin@bntu.by', '$2a$12$KUH7ZNm03PMeY/OggsB2wOAIvyTwPlZmHPolNYp5SJ7i5kym2o8du', '+375291234567', 0, false);

INSERT INTO users.student(id, sname, fname, mname, faculty_id, group_number, gender, age, residence)
VALUES
    ('d122cdbd-36f1-4017-9b40-46729716980f', 'Бульбочка', 'Вячеслав', 'Дмитриевич', '00000000-0000-0000-0000-000000000007', 10701122,'MALE', 21, 'Общежитие');

--rollback DELETE FROM users.student
--rollback WHERE id IN (
--rollback   'd122cdbd-36f1-4017-9b40-46729716980f'
--rollback   );
--rollback DELETE FROM users.admin
--rollback WHERE id IN (
--rollback   'e1cb5ed2-6850-4766-be54-75a8474cfb7c'
--rollback   );
