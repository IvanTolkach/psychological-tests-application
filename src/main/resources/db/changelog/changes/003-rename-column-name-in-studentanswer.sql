--liquibase formatted sql
--changeset ivantolkach:003-rename-column-name-in-studentanswer
ALTER TABLE StudentAnswer RENAME COLUMN selected_option_id TO answer_option_id;
ALTER TABLE StudentAnswer ALTER COLUMN answer_option_id DROP NOT NULL;

--rollback ALTER TABLE StudentAnswer ALTER COLUMN answer_option_id SET NOT NULL,
--rollback ALTER TABLE StudentAnswer RENAME COLUMN answer_option_id TO selected_option_id;
