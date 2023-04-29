-- liquibase formatted sql

-- changeset Homychok:1
CREATE TABLE avatar (
                        id         BIGSERIAL PRIMARY KEY,
                        data       BYTEA,
                        file_path  VARCHAR(255),
                        file_size  BIGINT,
                        media_type VARCHAR(255),
                        student_id BIGINT REFERENCES student (id)
);

