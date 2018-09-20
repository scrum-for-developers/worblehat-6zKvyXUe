-- liquibase formatted sql

-- changeset action:addColumn
ALTER TABLE book add description longtext NULL;