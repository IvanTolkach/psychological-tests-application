CREATE DATABASE psychologicalTestsDB
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

\connect psychologicaltestsdb

CREATE SCHEMA IF NOT EXISTS users;
CREATE SCHEMA IF NOT EXISTS methodologies;
CREATE SCHEMA IF NOT EXISTS tests;
CREATE SCHEMA IF NOT EXISTS attempts;

GRANT USAGE ON SCHEMA users TO public;
GRANT USAGE ON SCHEMA methodologies TO public;
GRANT USAGE ON SCHEMA tests TO public;
GRANT USAGE ON SCHEMA attempts TO public;

\q

--.\psql -U postgres -h localhost -p 5432
--\i C:/Users/PC/Documents/JavaProjects/psychological-tests-application/scripts/init-db.sql