--liquibase formatted sql
--changeset ivantolkach:003-filling-admin-and-user
--comment Начальное создание SUPER администратора, а также студентов для тестирования программы

INSERT INTO users.admin(id, sname, fname, mname, email, password, phone_number, role, is_active)
VALUES
    ('e1cb5ed2-6850-4766-be54-75a8474cfb7c', 'Инициализационный', 'Администратор', 'Системы', 'barin@bntu.by', '$2a$12$KUH7ZNm03PMeY/OggsB2wOAIvyTwPlZmHPolNYp5SJ7i5kym2o8du', '+375291234567', 1, true);

--rollback DELETE FROM users.admin
--rollback WHERE id IN (
--rollback   'e1cb5ed2-6850-4766-be54-75a8474cfb7c'
--rollback   );
